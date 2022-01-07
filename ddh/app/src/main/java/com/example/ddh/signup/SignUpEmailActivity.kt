package com.example.ddh.signup

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.ddh.R
import com.example.ddh.data.repository.UserRepositoryImpl
import com.example.ddh.databinding.ActivitySignUpEmailBinding
import com.example.ddh.main.MainActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import java.util.regex.Pattern


class SignUpEmailActivity : Activity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private val context: Context = this

    private lateinit var dataBinding: ActivitySignUpEmailBinding

    private val userRepository = UserRepositoryImpl() // 의존성 주입을 위한 Repository 객체 생성
    private val signUpViewmodel = SignUpViewModel(userRepository)

    private var userInfoHashMap = HashMap<String, String>()

    private lateinit var mCountDownTimer: CountDownTimer
    private var imageBtnClicked: Boolean = false
    private var _millisUntilFinished: Long = 0L
    private var isFirstPasswordInput = false
    private var isEmailVerified = false
    private var isNicknameUsable = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_email)
        dataBinding.vm = signUpViewmodel

        setButtons()
        setImageBtn()
        setCheckBox()
        setAgreementsDescriptionBtn()
        setEditTextObserver()
    }

    private fun setEditTextObserver() {
        io.reactivex.Observable.combineLatest(
            RxTextView.textChanges(dataBinding.etSignUpEmail),
            RxTextView.textChanges(dataBinding.etSignUpFirstPw),
            RxTextView.textChanges(dataBinding.etSignUpSecondPw),
            RxTextView.textChanges(dataBinding.etSignUpName),
            RxTextView.textChanges(dataBinding.etSignUpNickname),
            RxTextView.textChanges(dataBinding.etSignUpPhoneNumber),
//            RxTextView.textChanges(dataBinding.etSignUpBirth),
            { rxEmail, rxPassword1, rxPassword2, rxName, rxNickname, rxPhone ->

                checkEmail(rxEmail); checkFirstPassword(rxPassword1); checksecondPassword(rxPassword2); checkname(rxName); checkNickname(rxNickname); checkPhoneNumber(rxPhone)
                if (isFirstPasswordInput) {
                    if (checkFirstAndSecondPassword(rxPassword1) && checkEmail(rxEmail) && checkFirstPassword(rxPassword1) && checksecondPassword(rxPassword2) && checkname(rxName) && checkPhoneNumber(rxPhone)
                    ) {
                        dataBinding.btnSignUp.isEnabled = true
                        dataBinding.btnSignUp.setBackgroundResource(R.drawable.button_round_sign_up_activate)
                    } else {
                        dataBinding.btnSignUp.isEnabled = false
                        dataBinding.btnSignUp.setBackgroundResource(R.drawable.button_round_sign_up_enabled)
                    }
                } else {
                    if (checkEmail(rxEmail) && checkFirstPassword(rxPassword1) && checksecondPassword(rxPassword2) && checkname(rxName) && checkPhoneNumber(rxPhone)) {
                        dataBinding.btnSignUp.isEnabled = true
                        dataBinding.btnSignUp.setBackgroundResource(R.drawable.button_round_sign_up_activate)
                    } else {
                        dataBinding.btnSignUp.isEnabled = false
                        dataBinding.btnSignUp.setBackgroundResource(R.drawable.button_round_sign_up_enabled)
                    }
                }
            }
        ).subscribe()
    }

//    private fun setRxTextview() {
//        RxTextView.textChanges(dataBinding.etSignUpEmail).subscribe{ if (it.isNotEmpty()) checkEmail(it) }
//        RxTextView.textChanges(dataBinding.etSignUpFirstPw).subscribe{ if (it.isNotEmpty()) {
//            checkFirstPassword(it)
//            if(isFirstPasswordInput) {
//                checkFirstAndSecondPassword(it)
//            }
//        } }
//        RxTextView.textChanges(dataBinding.etSignUpSecondPw).subscribe{ if (it.isNotEmpty()) checksecondPassword(it) }
//        RxTextView.textChanges(dataBinding.etSignUpName).subscribe{ if (it.isNotEmpty()) checkname(it) }
//        RxTextView.textChanges(dataBinding.etSignUpPhoneNumber).subscribe{ if (it.isNotEmpty()) checkPhoneNumber(it) }
//        RxTextView.textChanges(dataBinding.etSignUpBirth).subscribe{ if (it.isNotEmpty()) checkBirth(it) }
//    }

    private fun setAgreementsDescriptionBtn() {
        dataBinding.btnProcedureUsing.setOnClickListener(this)
        dataBinding.btnProcedurePrivate.setOnClickListener(this)
        dataBinding.btnProcedureMarketing.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        var dialogueTitle = ""
        val agreementDialogue = AlertDialog.Builder(context)
        when (v!!.id) {
            R.id.btn_procedure_using -> {
                dialogueTitle = "이용약관 동의";agreementDialogue.setMessage(R.string.agreement_of_using)
            }
            R.id.btn_procedure_private -> {
                dialogueTitle = "개인정보 취급방침 동의"; agreementDialogue.setMessage(R.string.agreement_of_private)
            }
            R.id.btn_procedure_marketing -> {
                dialogueTitle = "마케팅 정보 수신 동의"; agreementDialogue.setMessage(R.string.agreement_of_marketing)
            }
        }
        agreementDialogue.setTitle(dialogueTitle)
        agreementDialogue.setPositiveButton("동의") { _: DialogInterface, _: Int ->

        }
        agreementDialogue.show()
    }

    private fun setCheckBox() {
        dataBinding.cbUsing.setOnCheckedChangeListener(this)
        dataBinding.cbPrivate.setOnCheckedChangeListener(this)
        dataBinding.cbMarketing.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (dataBinding.cbUsing.isChecked && dataBinding.cbPrivate.isChecked && dataBinding.cbMarketing.isChecked) {
            dataBinding.ibCheckAll.setImageResource(R.drawable.icon_red_check)
//            dataBinding.lineOfCheckbox.setBackgroundColor(Color.rgb(203, 61, 61))
        } else {
            dataBinding.ibCheckAll.setImageResource(R.drawable.icon_agreement)
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
/*        dataBinding.radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rg_men -> {
                    gender = "남자"
                }
                R.id.rg_women -> {
                    gender = "여자"
                }
            }
        }
        if (dataBinding.rgMen.isChecked) {
        } else {
        }*/
    }

/*    private fun checkBirth(s: CharSequence?): Boolean {
        val birthRegEx = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\$"
        if (Pattern.matches(birthRegEx, s)) {
            dataBinding.etSignUpBirth.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvBirthVaildCheck.visibility = View.GONE
            return true
        } else {
            if (s!!.isNotEmpty()) {
                dataBinding.etSignUpBirth.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorMainRed)
                dataBinding.tvBirthVaildCheck.text = "올바른 생년월일이 아닙니다."
                dataBinding.tvBirthVaildCheck.visibility = View.VISIBLE
            } else {
                dataBinding.etSignUpBirth.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
                dataBinding.tvBirthVaildCheck.visibility = View.GONE
            }
            return false
        }

    }*/

    private fun checkPhoneNumber(s: CharSequence?): Boolean {
        val phoneNumberRegEx = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$"
        if (Pattern.matches(phoneNumberRegEx, s)) {
            dataBinding.etSignUpPhoneNumber.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvPhoneVaildCheck.visibility = View.GONE
            return true
        } else {
            if (s!!.isNotEmpty()) {
                dataBinding.etSignUpPhoneNumber.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorMainRed)
                dataBinding.tvPhoneVaildCheck.text = "올바른 휴대폰 번호가 아닙니다"
                dataBinding.tvPhoneVaildCheck.visibility = View.VISIBLE
            } else {
                dataBinding.etSignUpPhoneNumber.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
                dataBinding.tvPhoneVaildCheck.visibility = View.GONE
            }
            return false
        }
    }

    private fun checkNickname(s: CharSequence?) {
        if(s!!.length > 2) {
            dataBinding.btnCheckNickname.isEnabled = true
        }
    }

    private fun checkname(s: CharSequence?): Boolean {
        val hangulRegEx = "^[가-힣]+$"
        val englishRegEx = "^[a-zA-Z]+$"
        if (Pattern.matches(hangulRegEx, s) || Pattern.matches(englishRegEx, s)) {
            dataBinding.etSignUpName.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvNameVaildCheck.visibility = View.GONE
            return true
        } else {
            if (s!!.isNotEmpty()) {
                dataBinding.etSignUpName.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorMainRed)
                dataBinding.tvNameVaildCheck.text = "한글로만 또는 영어로만 입력 가능합니다"
                dataBinding.tvNameVaildCheck.visibility = View.VISIBLE
            } else {
                dataBinding.etSignUpName.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
                dataBinding.tvNameVaildCheck.visibility = View.GONE
            }
            return false
        }
    }

    private fun checkFirstAndSecondPassword(s: CharSequence?): Boolean {
        if (dataBinding.etSignUpSecondPw.text.toString() != s?.toString()) {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorMainRed)
            dataBinding.tvSecondPasswordVaildCheck.text = "비밀번호가 일치하지 않습니다"
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.VISIBLE
            return true
        } else {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.GONE
            return false
        }
    }

    private fun checksecondPassword(s: CharSequence?): Boolean {
        if (dataBinding.etSignUpFirstPw.text.toString() == s?.toString()) {
            dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvSecondPasswordVaildCheck.visibility = View.GONE
            return true
        } else {
            if (s!!.isNotEmpty()) {
                dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorMainRed)
                dataBinding.tvSecondPasswordVaildCheck.text = "비밀번호가 일치하지 않습니다"
                dataBinding.tvSecondPasswordVaildCheck.visibility = View.VISIBLE
            } else {
                dataBinding.etSignUpSecondPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
                dataBinding.tvSecondPasswordVaildCheck.visibility = View.GONE
            }
            return false
        }
    }

    /**
     * 비밀번호 유효성 검증
     *   1. 8~20 글자 길이 충족
     *   2. 특수문자 1개 이상
     * */
    private fun checkFirstPassword(s: CharSequence?): Boolean {
        val passwordRegEx = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[\$`~!@\$!%*#^?&\\\\(\\\\)\\-_=+]).{7,21}\$"
        if (s!!.isNotEmpty()) {
            if (s?.length!! < 8 || s?.length!! > 20) {
                dataBinding.etSignUpFirstPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorMainRed)
                dataBinding.tvFirstPasswordVaildCheck.text = "비밀번호 길이는 8자리 이상 20자리 이하로 설정해주세요"
                dataBinding.tvFirstPasswordVaildCheck.visibility = View.VISIBLE
                return false
            } else if (!Pattern.matches(passwordRegEx, s)) {
                dataBinding.etSignUpFirstPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorMainRed)
                dataBinding.tvFirstPasswordVaildCheck.text = "비밀번호는 숫자, 문자, 특수문자를 모두 1개 이상 포함해야합니다"
                dataBinding.tvFirstPasswordVaildCheck.visibility = View.VISIBLE
                return false
            } else {
                dataBinding.etSignUpFirstPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
                dataBinding.tvFirstPasswordVaildCheck.visibility = View.GONE
                return true
            }
        } else {
            dataBinding.etSignUpFirstPw.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvFirstPasswordVaildCheck.visibility = View.GONE
            return false
        }
    }

/*    private fun checkVerifyingCode(): Boolean {
        if (_millisUntilFinished >= 0) {
            if (dataBinding.edtSignUpVerifyEmail.text.toString() == this.emailVerifyingCode) {
                userInfoHashMap["email"] = dataBinding.etSignUpEmail.text.toString()
                dataBinding.btnVerifyCode.text = "인증완료"
                dataBinding.btnVerifyCode.isEnabled = false
                dataBinding.edtSignUpVerifyEmail.isEnabled = false
                dataBinding.tvTimer.visibility = View.GONE
                dataBinding.tvVerifyCode.visibility = View.GONE
                dataBinding.edtSignUpVerifyEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
                dataBinding.btnRequestCode.visibility = View.GONE
                return true

            } else {
                dataBinding.tvVerifyCode.visibility = View.VISIBLE
                dataBinding.tvVerifyCode.text = "인증코드가 일치하지 않습니다."
                dataBinding.edtSignUpVerifyEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorMainRed)
                return false
            }
        } else {
            Toast.makeText(context, "시간이 초과되었습니다. 다시 인증코드를 받으세요.", Toast.LENGTH_SHORT).show()
            return false
        }
    }*/

    private fun checkEmail(s: CharSequence?): Boolean {
        val emailRegEx = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
        return if (Pattern.matches(emailRegEx, s)) {
            dataBinding.etSignUpEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            dataBinding.tvEmailVaildCheck.visibility = View.GONE
            dataBinding.btnVerifyEmail.isEnabled = true
            true
        } else {
            if (s!!.isNotEmpty()) {
                dataBinding.tvEmailVaildCheck.text = "올바른 이메일 형식이 아닙니다"
                dataBinding.tvEmailVaildCheck.visibility = View.VISIBLE
                dataBinding.etSignUpEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorMainRed)
                dataBinding.btnVerifyEmail.isEnabled = false
            } else {
                dataBinding.tvEmailVaildCheck.visibility = View.GONE
                dataBinding.etSignUpEmail.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.colorEditTextGrey)
            }
            false
        }
    }

    private fun setButtons() {
        // [인증요청] 버튼
        dataBinding.btnVerifyEmail.isClickable = false

        /** 이벤트 리스너 등록 */
        dataBinding.btnVerifyEmail.setOnClickListener {
            val verifyEmailHashMap = HashMap<String, String>()
            Log.d("[GET] Verify Email", verifyEmailHashMap["email"].toString())
            userRepository.getVerfyEmail(
                dataBinding.etSignUpEmail.text.toString(),
                success = {
                    it.run {
                        var builder = AlertDialog.Builder(context)
                        builder.setTitle("이메일 중복확인")
                        when (code) {
                            0 -> {
                                builder.setMessage("사용가능한 이메일입니다.")
                                var listener = DialogInterface.OnClickListener { _, p1 ->
                                    when (p1) {
                                        DialogInterface.BUTTON_POSITIVE -> {
                                            dataBinding.etSignUpEmail.isEnabled = false
                                            isEmailVerified = true
                                        }
                                    }
                                }
                                builder.setPositiveButton("사용하기", listener)
                                builder.show()

                            }
                            1001 -> {
                                builder.setMessage("이미 사용중인 이메일입니다.")
                                var listener = DialogInterface.OnClickListener { _, p1 ->
                                    when (p1) {
                                        DialogInterface.BUTTON_POSITIVE -> {
                                        }
                                    }
                                }
                                builder.setPositiveButton("닫기", listener)
                                builder.show()

                                Log.d("POST Verifying Wrong", "Error Code :${it.code}")
                            }
                        }
                    }
                },
                fail = {
                    Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                    Log.d("Post Verifying Email", "Fail to get verifying code : ${it.message}")
                }
            )
        }

        // [중복확인] 닉네임
        dataBinding.btnCheckNickname.setOnClickListener {
            userRepository.getNicknameCheck(
                dataBinding.etSignUpNickname.text.toString(),
                success = {
                    it.run {
                        var builder = AlertDialog.Builder(context)
                        builder.setTitle("닉네임 중복확인")
                        when (code) {
                            0 -> {
                                builder.setMessage("사용가능한 닉네임입니다.")
                                var listener = DialogInterface.OnClickListener { _, p1 ->
                                    when (p1) {
                                        DialogInterface.BUTTON_POSITIVE -> {
                                            isNicknameUsable = true
                                            dataBinding.etSignUpNickname.isEnabled = false
                                        }
                                    }
                                }
                                builder.setPositiveButton("사용하기", listener)
                                builder.show()

                            }
                            1006 -> {
                                builder.setMessage("이미 사용중인 이메일입니다.")
                                var listener = DialogInterface.OnClickListener { _, p1 ->
                                    when (p1) {
                                        DialogInterface.BUTTON_POSITIVE -> {
                                        }
                                    }
                                }
                                builder.setPositiveButton("닫기", listener)
                                builder.show()

                                Log.d("POST Verifying Wrong", "Error Code :${it.code}")
                            }
                        }
                    }
                },
                fail = {

                }
            )
        }

        // [가입하기] 버튼
        dataBinding.btnSignUp.setOnClickListener {
            if (isEmailVerified) {
                if (isNicknameUsable) {
                    if (dataBinding.cbMarketing.isChecked && dataBinding.cbMarketing.isChecked && dataBinding.cbMarketing.isChecked) {
                        dataBinding.lineOfCheckbox.setBackgroundColor(Color.rgb(221, 221, 221))
                        dataBinding.btnSignUp.isEnabled = true
                        userInfoHashMap["email"] = dataBinding.etSignUpEmail.text.toString()
                        userInfoHashMap["password"] = dataBinding.etSignUpSecondPw.text.toString()
                        userInfoHashMap["name"] = dataBinding.etSignUpName.text.toString()
                        userInfoHashMap["nickName"] = dataBinding.etSignUpNickname.text.toString()
                        userInfoHashMap["tel"] = dataBinding.etSignUpPhoneNumber.text.toString()
                        userRepository.postSignUp(
                            userInfoHashMap,
                            success = {
                                it.run {
                                    val intent = Intent(this@SignUpEmailActivity, MainActivity::class.java)
                                    intent.putExtra("username", it.data!!.email)
                                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                                    finish()
                                }
                                Log.d("Button Sign Up", "Success to SignUp")
                            },
                            fail = {
                                Toast.makeText(this@SignUpEmailActivity, "${it.message}", Toast.LENGTH_SHORT).show()
                                Log.d("Post Sign Up Method", it.message.toString())
                            }

                        )
                    } else { // 이용약관 동의 체크하지 않은 경우
                        dataBinding.lineOfCheckbox.setBackgroundColor(Color.argb(239, 221, 34, 34))
                        Toast.makeText(context, "이용약관에 동의 해주세요.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "사용하실 닉네임 중복확인이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "이메일 인증이 필요합니다.", Toast.LENGTH_SHORT).show()
            }

        }

        //[뒤로가기 <-] 버튼
        dataBinding.btnBackToSocialLoginActivity.setOnClickListener {
            finish()
        }


    }

/*    private fun setTimer() {
        val dateFormat = SimpleDateFormat("mm:ss")
        mCountDownTimer = object : CountDownTimer(300 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _millisUntilFinished = millisUntilFinished
                tvTimer.text = dateFormat.format(millisUntilFinished).toString()
            }

            override fun onFinish() {

            }

        }.start()
    }*/

}
