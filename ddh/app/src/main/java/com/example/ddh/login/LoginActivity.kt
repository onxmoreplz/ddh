package com.example.ddh.login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ddh.R
import com.example.ddh.data.repository.UserRepositoryImpl
import com.example.ddh.databinding.ActivityLoginBinding
import com.example.ddh.main.MainActivity
import com.example.ddh.signup.SignUpEmailActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause

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
        setSocialLoginBtn()
        setBtn()
    }

    private fun setSocialLoginBtn() {
        setKakaoLogin()
    }

    private fun setKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("Kakao Login", error.toString())
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        // [카카오 로그인] 버튼 리스너
        databinding.btnKakaoLogin.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                Log.d("Kakao Login FAIL", "There is no KakaoTalk App in this phone")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.kakao.talk&hl=ko&gl=US"))
                startActivity(intent)
            }
        }


    }

    private fun setBtn() {
        // [로그인] 버튼
        databinding.btnLogin.setOnClickListener {
            if (databinding.etEmail.toString().isNotEmpty()) {
                if (databinding.etPw.toString().isNotEmpty()) {
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
                Log.e("postUserLogin", "Fail to Login : ${it.message}")
            }
        )
    }
}