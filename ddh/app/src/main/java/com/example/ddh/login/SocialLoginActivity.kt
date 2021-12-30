package com.example.ddh.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ddh.R
import com.example.ddh.databinding.ActivitySocialLoginBinding
import com.example.ddh.signup.SignUpEmailActivity

class SocialLoginActivity : Activity() {

    private lateinit var databinding: ActivitySocialLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Ddh)// 스플래시로 인해 변경되었던 Theme 되돌림

        super.onCreate(savedInstanceState)

        databinding = DataBindingUtil.setContentView(this, R.layout.activity_social_login)

        setTextView()
    }

    private fun setTextView() {
        databinding.tvLoginByEmail.setOnClickListener {
            startActivity(Intent(this, LocalLoginActivity::class.java))
        }
        databinding.tvSignUpForEmail.setOnClickListener {
            startActivity(Intent(this, SignUpEmailActivity::class.java))
        }
    }
}
