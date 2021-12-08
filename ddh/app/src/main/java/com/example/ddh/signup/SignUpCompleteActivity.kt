package com.example.ddh.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.example.ddh.R
import com.example.ddh.login.LoginActivity


class SignUpCompleteActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_complete)

        setTvUserName()
        setHandlerToBackLoginActivityAfter8seconds()

    }

    private fun setHandlerToBackLoginActivityAfter8seconds() {
        Handler().postDelayed({
            startActivity(Intent(this@SignUpCompleteActivity, LoginActivity::class.java))
        }, 10000)
    }

    private fun setTvUserName() {
        val intent = intent
        val userName = intent.getStringExtra("username")

        findViewById<TextView>(R.id.tv_user_name).text = "$userName 님 \n등산의 목적 가입이 완료되었습니다.\n지금부터 즐거운 등산을 시작해보세요!"
        findViewById<Button>(R.id.btn_back_login).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}