package com.example.ddh.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.recyclerview.widget.RecyclerView
import com.example.ddh.R
import com.example.ddh.data.repository.UserRepositoryImpl
import com.example.ddh.databinding.ActivitySignUpEmailBinding
import java.text.SimpleDateFormat
import java.util.regex.Pattern


class SignUpEmailActivity : Activity() {

    private lateinit var dataBinding: ActivitySignUpEmailBinding

    private val userRepository = UserRepositoryImpl() // 의존성 주입을 위한 Repository 객체 생성
    private val signUpViewmodel = SignUpViewModel(userRepository)

    private var isValidArrayList: ArrayList<Boolean> = arrayListOf(false, false, false, false, false, false, true, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_email)
        dataBinding.vm = signUpViewmodel

        setVerifyBtn()
        setCheckBoxRecyclerView()
        setEditText()
        setRadioBtn()
        observeViewModelEvent()

    }

    private fun setRadioBtn() {
        if(dataBinding.rgMen.isChecked) {}
        else {}
    }

    private fun setEditText() {
        dataBinding.etSignUpEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEmail(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        dataBinding.etSignUpFirstPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFirstPassword(s)
                checkFirstAndSecondPassword(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        dataBinding.etSignUpSecondPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checksecondPassword(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        dataBinding.etSignUpName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkname(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        dataBinding.etSignUpPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPhoneNumber(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        dataBinding.etSignUpBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkBirth(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun checkBirth(s: CharSequence?) {
        val birthRegEx = "[1-2][0-9]{3}[0-1][0-9][0-3][0-9]"
        if (Pattern.matches(birthRegEx, s)) {
            dataBinding.etSignUpBirth.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGreen)
            dataBinding.tvBirthVaildCheck.visibility = View.GONE
            isValidArrayList[5] = true
        } else {
            dataBinding.etSignUpBirth.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvBirthVaildCheck.text = "올바른 생년월일이 아닙니다."
            dataBinding.tvBirthVaildCheck.visibility = View.VISIBLE
            isValidArrayList[5] = false
        }

    }

    private fun checkPhoneNumber(s: CharSequence?) {
        val phoneNumberRegEx = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$"
        if (Pattern.matches(phoneNumberRegEx, s)) {
            dataBinding.etSignUpPhoneNumber.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGreen)
            dataBinding.tvPhoneVaildCheck.visibility = View.GONE
            isValidArrayList[4] = true
        } else {
            dataBinding.etSignUpPhoneNumber.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvPhoneVaildCheck.text = "올바른 휴대폰 번호가 아닙니다"
            dataBinding.tvPhoneVaildCheck.visibility = View.VISIBLE
            isValidArrayList[4] = false
        }
    }

    private fun checkname(s: CharSequence?) {
        val hangulRegEx = "^[가-힣]+$"
        val englishRegEx = "^[a-zA-Z]+$"
        if (Pattern.matches(hangulRegEx, s) || Pattern.matches(englishRegEx, s)) {
            dataBinding.etSignUpName.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGreen)
            dataBinding.tvNameVaildCheck.visibility = View.GONE
            isValidArrayList[3] = true
        } else {
            dataBinding.etSignUpName.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvNameVaildCheck.text = "한글로만 또는 영어로만 입력 가능합니다"
            dataBinding.tvNameVaildCheck.visibility = View.VISIBLE
            isValidArrayList[3] = false
        }
    }

    private fun checkFirstAndSecondPassword(s: CharSequence?) {
        if (dataBinding.etSignUpSecondPw.text.toString() != s?.toString()){
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvSecondPasswordVaildCheck.text = "비밀번호가 일치하지 않습니다"
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.VISIBLE
            isValidArrayList[2] = false
        } else {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGreen)
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.GONE
            isValidArrayList[2] = true
        }
    }

    private fun checksecondPassword(s: CharSequence?) {
        if (dataBinding.etSignUpFirstPw.text.toString() == s?.toString()) {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGreen)
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.GONE
            isValidArrayList[2] = true
        } else {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvSecondPasswordVaildCheck.text = "비밀번호가 일치하지 않습니다"
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.VISIBLE
            isValidArrayList[2] = false
        }
        Log.d("equals", dataBinding.etSignUpFirstPw.text.toString() + " == " + s)
    }

    /**
     * 비밀번호 유효성 검증
     *   1. 8~20 글자 길이 충족
     *   2. 특수문자 1개 이상
     * */
    private fun checkFirstPassword(s: CharSequence?) {
        val passwordRegEx = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[\$`~!@\$!%*#^?&\\\\(\\\\)\\-_=+]).{7,21}\$"
        if (s?.length!! < 8 || s?.length!! > 20) {
            dataBinding.etSignUpFirstPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvFirstPasswordVaildCheck.text = "비밀번호 길이는 8자리 이상 20자리 이하로 설정해주세요"
            dataBinding.tvFirstPasswordVaildCheck.visibility = View.VISIBLE
            isValidArrayList[1] = false
        } else if (!Pattern.matches(passwordRegEx, s)) {
            dataBinding.etSignUpFirstPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvFirstPasswordVaildCheck.text = "비밀번호는 숫자, 문자, 특수문자를 모두 1개 이상 포함해야합니다"
            dataBinding.tvFirstPasswordVaildCheck.visibility = View.VISIBLE
            isValidArrayList[1] = false
        } else {
            dataBinding.etSignUpFirstPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGreen)
            dataBinding.tvFirstPasswordVaildCheck.visibility = View.GONE
            isValidArrayList[1] = true
        }


    }

    private fun checkEmail(s: CharSequence?) {
        val emailRegEx = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
        if (Pattern.matches(emailRegEx, s)) {
            dataBinding.etSignUpEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGreen)
            dataBinding.tvEmailVaildCheck.visibility = View.GONE
            isValidArrayList[0] = true
        } else {
            dataBinding.tvEmailVaildCheck.text = "올바른 이메일 형식이 아닙니다"
            dataBinding.tvEmailVaildCheck.visibility = View.VISIBLE
            dataBinding.etSignUpEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            isValidArrayList[0] = false
        }
    }

    private fun observeViewModelEvent() {

        // [가입하기] 버튼 클릭
        signUpViewmodel.startSignUpCompleteActivity.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
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

        if (findViewById<CheckBox>(R.id.cb_procecure_all).isChecked) {
            Toast.makeText(this, "hot~", Toast.LENGTH_SHORT).show()
        } else {
            //액티비티 시작 시 여기 걸림
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
