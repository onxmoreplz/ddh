package com.example.ddh.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ddh.R
import com.example.ddh.login.LoginActivity


class SignUpCompleteActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_complete)

        val intent = intent
        val userName = intent.getStringExtra("username")

        findViewById<TextView>(R.id.tv_user_name).text = "$userName 님 \nDDH의 회원이 되신 것을\n축하드립니다."
        findViewById<Button>(R.id.btn_back_login).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}