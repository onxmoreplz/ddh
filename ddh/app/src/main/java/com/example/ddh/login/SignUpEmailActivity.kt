package com.example.ddh.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.ddh.R

class SignUpEmailActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_email)

        setVerifyBtn()
        setTvCannotGetCode()
        setNextBtn()
    }

    private fun setNextBtn() {
        findViewById<Button>(R.id.btn_sign_up_email_next).setOnClickListener {
            val intent = Intent(this, SignUpPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setTvCannotGetCode() {
        var spannableString = SpannableString("인증번호를 받지 못하셨나요?")
        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)

        findViewById<TextView>(R.id.tv_cannot_get_code).text = spannableString
    }

    private fun setVerifyBtn() {
        findViewById<Button>(R.id.btn_verify_email).setOnClickListener {
            findViewById<View>(R.id.ll__verify_code_from_email).visibility = View.VISIBLE
        }
    }
}