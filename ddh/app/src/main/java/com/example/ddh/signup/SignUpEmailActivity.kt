package com.example.ddh.signup

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.example.ddh.R
import com.example.ddh.data.repository.UserRepositoryImpl
import com.example.ddh.databinding.ActivitySignUpEmailBinding
import java.text.SimpleDateFormat
import java.util.regex.Pattern
import kotlin.math.absoluteValue


class SignUpEmailActivity : Activity(), CompoundButton.OnCheckedChangeListener {

    private val context: Context = this

    private lateinit var dataBinding: ActivitySignUpEmailBinding

    private val userRepository = UserRepositoryImpl() // 의존성 주입을 위한 Repository 객체 생성
    private val signUpViewmodel = SignUpViewModel(userRepository)

    private var userInfoHashMap = HashMap<String, String>()
    private var isValidArrayList: HashMap<String, Boolean> = HashMap()

    private var imageBtnClicked: Boolean = false
    private lateinit var tvTimer: TextView
    private lateinit var emailVerifyingCode: String
    private var millisUntilFinished: Long = 0L
    private var gender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_email)
        dataBinding.vm = signUpViewmodel

        setButtons()
        setEditText()
        setRadioGroup()
        setImageBtn()
        setCheckBox()
        observeViewModelEvent()

    }

    private fun setCheckBox() {
        dataBinding.cbUsing.setOnCheckedChangeListener(this)
        dataBinding.cbUsing.setOnCheckedChangeListener(this)
        dataBinding.cbUsing.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (dataBinding.cbUsing.isChecked && dataBinding.cbPrivate.isChecked && dataBinding.cbMarketing.isChecked) {
            dataBinding.ibCheckAll.setBackgroundResource(R.drawable.confirmed)
        } else {
            dataBinding.ibCheckAll.setBackgroundResource(R.drawable.check)
        }
    }

    private fun setImageBtn() {
        dataBinding.ibCheckAll.setOnClickListener {
            if (imageBtnClicked) {
                dataBinding.cbUsing.isChecked = false; dataBinding.cbPrivate.isChecked = false; dataBinding.cbMarketing.isChecked = false
                imageBtnClicked = false
            } else {
                dataBinding.cbUsing.isChecked = true; dataBinding.cbPrivate.isChecked = true; dataBinding.cbMarketing.isChecked = true
                imageBtnClicked = true
            }
        }
    }

    private fun setRadioGroup() {
        dataBinding.radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.rg_men -> { gender = "남자" }
                R.id.rg_women -> {gender = "여자"}
            }
        }
        if (dataBinding.rgMen.isChecked) {
        } else {
        }
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
            dataBinding.etSignUpBirth.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvBirthVaildCheck.visibility = View.GONE
            isValidArrayList["birth"] = true
        } else {
            dataBinding.etSignUpBirth.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvBirthVaildCheck.text = "올바른 생년월일이 아닙니다."
            dataBinding.tvBirthVaildCheck.visibility = View.VISIBLE
            isValidArrayList["birth"] = false
        }

    }

    private fun checkPhoneNumber(s: CharSequence?) {
        val phoneNumberRegEx = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$"
        if (Pattern.matches(phoneNumberRegEx, s)) {
            dataBinding.etSignUpPhoneNumber.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvPhoneVaildCheck.visibility = View.GONE
            isValidArrayList["phone"] = true
        } else {
            dataBinding.etSignUpPhoneNumber.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvPhoneVaildCheck.text = "올바른 휴대폰 번호가 아닙니다"
            dataBinding.tvPhoneVaildCheck.visibility = View.VISIBLE
            isValidArrayList["phone"] = false
        }
    }

    private fun checkname(s: CharSequence?) {
        val hangulRegEx = "^[가-힣]+$"
        val englishRegEx = "^[a-zA-Z]+$"
        if (Pattern.matches(hangulRegEx, s) || Pattern.matches(englishRegEx, s)) {
            dataBinding.etSignUpName.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvNameVaildCheck.visibility = View.GONE
            isValidArrayList["name"] = true
        } else {
            dataBinding.etSignUpName.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvNameVaildCheck.text = "한글로만 또는 영어로만 입력 가능합니다"
            dataBinding.tvNameVaildCheck.visibility = View.VISIBLE
            isValidArrayList["name"] = false
        }
    }

    private fun checkFirstAndSecondPassword(s: CharSequence?) {
        if (dataBinding.etSignUpSecondPw.text.toString() != s?.toString()) {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvSecondPasswordVaildCheck.text = "비밀번호가 일치하지 않습니다"
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.VISIBLE
        } else {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.GONE
        }
    }

    private fun checksecondPassword(s: CharSequence?) {
        if (dataBinding.etSignUpFirstPw.text.toString() == s?.toString()) {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.GONE
//            isValidArrayList["secondPassword"] = true
        } else {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvSecondPasswordVaildCheck.text = "비밀번호가 일치하지 않습니다"
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.VISIBLE
//            isValidArrayList["secondPassword"] = false
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
            isValidArrayList["firstPassword"] = false
        } else if (!Pattern.matches(passwordRegEx, s)) {
            dataBinding.etSignUpFirstPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            dataBinding.tvFirstPasswordVaildCheck.text = "비밀번호는 숫자, 문자, 특수문자를 모두 1개 이상 포함해야합니다"
            dataBinding.tvFirstPasswordVaildCheck.visibility = View.VISIBLE
            isValidArrayList["firstPassword"] = false
        } else {
            dataBinding.etSignUpFirstPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvFirstPasswordVaildCheck.visibility = View.GONE
            isValidArrayList["firstPassword"] = true
        }


    }

    private fun checkEmail(s: CharSequence?) {
        val emailRegEx = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
        if (Pattern.matches(emailRegEx, s)) {
            dataBinding.etSignUpEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvEmailVaildCheck.visibility = View.GONE
            isValidArrayList["email"] = true
            dataBinding.btnRequestCode.isEnabled = true
        } else {
            dataBinding.tvEmailVaildCheck.text = "올바른 이메일 형식이 아닙니다"
            dataBinding.tvEmailVaildCheck.visibility = View.VISIBLE
            dataBinding.etSignUpEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
            isValidArrayList["email"] = false
            dataBinding.btnRequestCode.isEnabled = false
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

    private fun setButtons() {
        tvTimer = dataBinding.tvTimer
        dataBinding.btnRequestCode.isClickable = false
        dataBinding.btnRequestCode.setOnClickListener {
            val verifyEmailHashMap = HashMap<String, String>()
            verifyEmailHashMap["email"] = dataBinding.etSignUpEmail.text.toString()
            userRepository.postVerfyEmail(
                verifyEmailHashMap,
                success = {
                    it.run {
                        if (it.code == 0) {
                            emailVerifyingCode = it.data!!.code!!
                            Log.d("Verifying code", it.data!!.code!!)

                            dataBinding.btnRequestCode.text = "재전송"
                            findViewById<LinearLayout>(R.id.ll__verify_code_from_email).visibility = View.VISIBLE
                            dataBinding.etSignUpEmail.setTypeface(null, Typeface.BOLD_ITALIC)
                            dataBinding.etSignUpEmail.isEnabled = false

                            setTimer()
                        } else {
                            val emailVerifyingDialogue = AlertDialog.Builder(context)
                            emailVerifyingDialogue.setTitle("이메일 중복확인")
                            emailVerifyingDialogue.setMessage(it.data!!.message)
                            emailVerifyingDialogue.setNeutralButton("닫기") { _: DialogInterface, i: Int ->

                            }
                            emailVerifyingDialogue.show()
                        }
                    }
                },
                fail = { Log.d("SignUp Activity", "Fail to get verifying code") }
            )

            dataBinding.btnVerifyCode.setOnClickListener {
                if (dataBinding.edtSignUpVerifyEmail.text.toString() == this.emailVerifyingCode) {
                    if (millisUntilFinished <= 0) {
                        isValidArrayList["verifyingCode"] = true
                        userInfoHashMap["email"] = dataBinding.etSignUpEmail.text.toString()
                        dataBinding.btnVerifyCode.text = "인증완료"
                        dataBinding.btnVerifyCode.setBackgroundColor(Color.rgb(240, 240, 240))
                        dataBinding.tvTimer.visibility = View.GONE
                    } else {
                        Toast.makeText(context, "시간이 초과되었습니다. 다시 인증코드를 받으세요.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    dataBinding.tvVerifyCode.text = "인증코드가 일치하지 않습니다."
                    dataBinding.edtSignUpVerifyEmail.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextRed)
                    isValidArrayList["verifyingCode"] = false
                }
            }

            dataBinding.btnSignUp.setOnClickListener {
                if (checkAllEditTextValue()) {
                    dataBinding.btnSignUp.isEnabled = true
                    userInfoHashMap["email"] = dataBinding.etSignUpEmail.text.toString()
                    userInfoHashMap["password"] = dataBinding.etSignUpSecondPw.text.toString()
                    userInfoHashMap["name"] = dataBinding.etSignUpName.text.toString()
                    userInfoHashMap["birthday"] = dataBinding.etSignUpBirth.text.toString()
                    userInfoHashMap["tel"] = dataBinding.etSignUpPhoneNumber.text.toString()
                    userInfoHashMap["gender"] = gender
                    userInfoHashMap["personalInformation"] = "true"
                    userInfoHashMap["recommendedCode"] = ""
                    userRepository.postSignUp(
                        userInfoHashMap,
                        success = {
                            it.run {
                                val intent = Intent(this@SignUpEmailActivity, SignUpCompleteActivity::class.java)
                                intent.putExtra("username", it.data!!.name)
                                startActivity(intent)
                            }
                            Log.d("Button Sign Up", "Succee to SignUp")
                        },
                        fail = { }

                    )
                }
                else {
                    Toast.makeText(context, "회원가입 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun checkAllEditTextValue(): Boolean {
        for ((key, value) in isValidArrayList) {
            if (!value) {
                Toast.makeText(context, key + "형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }


    private fun setTimer() {
        val dateFormat = SimpleDateFormat("mm:ss")
        val mCountDownTimer: CountDownTimer = object : CountDownTimer(300 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = dateFormat.format(millisUntilFinished).toString()
            }

            override fun onFinish() {

            }

        }.start()
    }

}
