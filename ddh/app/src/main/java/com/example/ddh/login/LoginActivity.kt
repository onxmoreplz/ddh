package com.example.ddh.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.ddh.R
import com.example.ddh.data.repository.UserRepositoryImpl
import com.example.ddh.databinding.ActivityLoginBinding
import com.example.ddh.main.MainActivity
import com.example.ddh.signup.SignUpEmailActivity
import java.util.regex.Pattern

class LoginActivity : Activity() {

    private lateinit var databinding: ActivityLoginBinding

    private val userRepository = UserRepositoryImpl() // 의존성 주입을 위한 Repository 객체 생성
    private val loginViewmodel = LoginViewModel(userRepository)

    private var isEmailValid: Boolean = false
    private var isPasswordValid: Boolean = false

    private var etEmail: String = ""
    private var etPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Ddh)// 스플래시로 인해 변경되었던 Theme 되돌림

        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        databinding.vm = loginViewmodel

//        setObserveViewModelEvent()
        setBtn()
    }

/*    private fun setObserveViewModelEvent() {
        // 이메일 유효성 검사
        loginViewmodel.emailChecking.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                databinding.etEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            }
        })
        // 로그인 성공
        loginViewmodel.successLogin.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })
        // 로그인 실패
        loginViewmodel.failLogin.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Toast.makeText(this@LoginActivity, "---", Toast.LENGTH_SHORT).show()
            }
        })
    }*/


    private fun setBtn() {
        // [로그인] 버튼
        databinding.btnLogin.setOnClickListener {
            if (databinding.etEmail.toString().isNotEmpty()) {
                if(databinding.etPw.toString().isNotEmpty()) {
                    etEmail = databinding.etEmail.text.toString().trim()
                    etPassword = databinding.etPw.text.toString().trim()
                    postUserLogin(etEmail, etPassword)
                } else {
                    Toast.makeText(this@LoginActivity, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@LoginActivity, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // [회원가입] 버튼
        databinding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpEmailActivity::class.java))
        }
    }

    private fun postUserLogin(email: String, password: String) {
        val loginHashMap = HashMap<String, String>()
        loginHashMap["email"] = email
        loginHashMap["password"] = password
        userRepository.postLogin(
            loginHashMap,
            success = {
                it.run {
                    when (code) {
                        0 -> { // 로그인 성공
                            val loginUserName: String = it.data!!.name!!
                            val bundle: Bundle
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else -> {
                            Toast.makeText(this@LoginActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            },
            fail = {
                Log.e("postUserLogin", "Fail to Login :")
            }
        )
    }
}