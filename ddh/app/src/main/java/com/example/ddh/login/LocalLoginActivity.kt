package com.example.ddh.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ddh.App
import com.example.ddh.R
import com.example.ddh.data.repository.UserRepositoryImpl
import com.example.ddh.databinding.ActivityLocalLoginBinding
import com.example.ddh.login.find.FindIdActivity
import com.example.ddh.login.find.FindPasswordActivity
import com.example.ddh.main.MainActivity
import com.example.ddh.signup.SignUpEmailActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause

class LocalLoginActivity : Activity() {

    private lateinit var databinding: ActivityLocalLoginBinding

    private val userRepository = UserRepositoryImpl() // 의존성 주입을 위한 Repository 객체 생성
    private val loginViewmodel = LoginViewModel(userRepository)

    private var isEmailValid: Boolean = false
    private var isPasswordValid: Boolean = false

    private var loginHashMap = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Ddh)// 스플래시로 인해 변경되었던 Theme 되돌림

        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_local_login)
        databinding.vm = loginViewmodel

//        setObserveViewModelEvent()
        setSocialLoginBtn()
        setBtnAndTextview()

        checkLoginCheckbox()
    }

    private fun checkLoginCheckbox() {
        if(App.sharedPrefs.getCheckBoxLoginInfo()!!) {
            databinding.etEmail.setText(App.sharedPrefs.getUserEmail())
            databinding.etPw.setText(App.sharedPrefs.getUserPassword())
            databinding.chSaveLoginInfo.isChecked = true
        } else {
            databinding.chSaveLoginInfo.isChecked = false
        }
    }

    private fun setSocialLoginBtn() {
        setKakaoLogin()
    }

    private fun setKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("Kakao Login", error.toString())
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()
            }
        }

        // [카카오 로그인] 버튼 리스너
/*        databinding.btnKakaoLogin.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                Log.d("Kakao Login FAIL", "There is no KakaoTalk App in this phone")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.kakao.talk&hl=ko&gl=US"))
                startActivity(intent)
            }
        }*/


    }

    private fun setBtnAndTextview() {
        // [로그인] 버튼
        var email: String? = ""
        var password: String? = ""
        databinding.btnLogin.setOnClickListener {
            if (databinding.etEmail.toString().isNotEmpty()) {
                if (databinding.etPw.toString().isNotEmpty()) {
                    email = databinding.etEmail.text.toString().trim()
                    password = databinding.etPw.text.toString().trim()
                    loginHashMap["email"] = email!!
                    loginHashMap["password"] = password!!

                    postUserLogin(loginHashMap)
                    checkBoxLoginInfo()
                } else {
                    Toast.makeText(this@LocalLoginActivity, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@LocalLoginActivity, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
        databinding.btnBackToSocialLoginActivity.setOnClickListener{
            finish()
        }

        // [회원가입] 버튼
        databinding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpEmailActivity::class.java))
        }
        databinding.tvFindId.setOnClickListener {
            startActivity(Intent(this, FindIdActivity::class.java))
        }
        databinding.tvFindPassword.setOnClickListener {
            startActivity(Intent(this, FindPasswordActivity::class.java))
        }
    }

    private fun checkBoxLoginInfo() {
        if (databinding.chSaveLoginInfo.isChecked) {
            App.sharedPrefs.setCheckBoxLoginInfo(true)
        } else {
            App.sharedPrefs.setCheckBoxLoginInfo(false)
        }
    }

    private fun postUserLogin(hashMapLogin: HashMap<String, String>) {
        userRepository.postLogin(
            hashMapLogin,
            success = {
                it.run {
                    when (code) {
                        0 -> { // 로그인 성공
                            App.sharedPrefs.setUserEmail(it.data!!.email!!)
                            App.sharedPrefs.setUserPassword(databinding.etPw.text.toString())
                            App.sharedPrefs.setUserName(it.data!!.name!!)
                            App.sharedPrefs.setUserNickname(it.data!!.nickName!!)
                            App.sharedPrefs.setUserTel(it.data!!.tel!!)
                            App.sharedPrefs.setUserAccessToken(it.data!!.accessToken!!)
                            App.sharedPrefs.setUserRefreshToken(it.data!!.refreshToken!!)

                            val intent = Intent(this@LocalLoginActivity, MainActivity::class.java)
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                            finish()
                        }
                        1001 -> {// 등록된 계정이 없음
                            Toast.makeText(this@LocalLoginActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                        }
                        1002 -> {// 비밀번호 불일치
                            Toast.makeText(this@LocalLoginActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                        }
                        1003 -> {//회원 잠김
                          Toast.makeText(this@LocalLoginActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                        }
                        1009 -> {//이메일 미인증 상태 계정
                            //TODO : 다이얼로그 만들 예정
                        }
                        else -> {
                            Toast.makeText(this@LocalLoginActivity, it.data!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        ) {
            Log.e("postUserLogin", "Fail to Login : ${it.message}")
            if( it.message == "timeout") {
                Toast.makeText(this@LocalLoginActivity, "인터넷 연결이 불안정합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}