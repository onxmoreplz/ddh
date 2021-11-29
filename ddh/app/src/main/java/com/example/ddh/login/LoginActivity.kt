package com.example.ddh.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.ddh.R
import com.example.ddh.data.repository.UserRepositoryImpl
import com.example.ddh.databinding.ActivityLoginBinding
import com.example.ddh.signup.SignUpEmailActivity

class LoginActivity : Activity() {

    private lateinit var databinding: ActivityLoginBinding

    private val userRepository = UserRepositoryImpl() // 의존성 주입을 위한 Repository 객체 생성
    private val loginViewmodel = LoginViewModel(userRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Ddh)// 스플래시로 인해 변경되었던 Theme 되돌림

        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        databinding.vm = loginViewmodel

        setBtn()
    }

    private fun setBtn() {
        findViewById<Button>(R.id.btn_sign_up).setOnClickListener {
        val intent = Intent(this, SignUpEmailActivity::class.java)
        startActivity(intent)
        }

    }
}