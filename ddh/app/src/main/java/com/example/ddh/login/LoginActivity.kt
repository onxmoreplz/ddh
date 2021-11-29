package com.example.ddh.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ddh.R
import com.example.ddh.signup.SignUpEmailActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Ddh)// 스플래시로 인해 변경되었던 Theme 되돌림

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setBtn()
    }

    private fun setBtn() {
        findViewById<Button>(R.id.btn_sign_up).setOnClickListener {
        val intent = Intent(this, SignUpEmailActivity::class.java)
        startActivity(intent)
        }
    }
}