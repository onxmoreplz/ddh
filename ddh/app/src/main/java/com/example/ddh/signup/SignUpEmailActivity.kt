package com.example.ddh.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.recyclerview.widget.RecyclerView
import com.example.ddh.R
import com.example.ddh.data.repository.UserRepositoryImpl
import com.example.ddh.databinding.ActivitySignUpEmailBinding
import java.text.SimpleDateFormat


class SignUpEmailActivity : Activity() {

    private lateinit var dataBinding: ActivitySignUpEmailBinding

    private val UserRepository = UserRepositoryImpl() // 의존성 주입을 위한 Repository 객체 생성
    private val signUpViewmodel = SignUpViewModel(UserRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_email)
        dataBinding.vm = signUpViewmodel

        setVerifyBtn()
        setCheckBoxRecyclerView()

        observeViewModelEvent()

    }

    private fun observeViewModelEvent() {

        // [가입하기] 버튼 클릭
        signUpViewmodel.startSignUpCompleteActivity.addOnPropertyChangedCallback( object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val intent = Intent(this@SignUpEmailActivity, SignUpCompleteActivity::class.java)
                intent.putExtra("username", signUpViewmodel.signUpResultUsername)
                startActivity(intent)
            }
        })
    }

    private fun setVerifyBtn() {
        val btnRequestCode: Button = findViewById(R.id.btn_request_code)
        btnRequestCode.setOnClickListener {
            btnRequestCode.text = "재전송"
            findViewById<LinearLayout>(R.id.ll__verify_code_from_email).visibility = View.VISIBLE

            setTimer()
        }
    }

    private fun setCheckBoxRecyclerView() {
        val agreementsAdapter = CheckboxAdapter(this)
        findViewById<RecyclerView>(R.id.rv_check_box).adapter = agreementsAdapter
        agreementsAdapter.notifyDataSetChanged()

        if(findViewById<CheckBox>(R.id.cb_procecure_all).isChecked) {
            Toast.makeText(this, "hot~", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "not!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setTimer() {
        val dateFormat = SimpleDateFormat("mm:ss")
        val mCountDownTimer: CountDownTimer = object : CountDownTimer(300 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                findViewById<TextView>(R.id.tv_timer).text = dateFormat.format(millisUntilFinished).toString()
            }

            override fun onFinish() {

            }

        }.start()
    }
}
