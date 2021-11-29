package com.example.ddh.signup

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ddh.R


class SignUpCompleteActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_complete)

        val intent = intent
        val userName = intent.getStringExtra("username")

        findViewById<TextView>(R.id.tv_user_name).text = "$userName 님 \nDDH의 회원이 되신 것을\n축하드립니다."


    }
}