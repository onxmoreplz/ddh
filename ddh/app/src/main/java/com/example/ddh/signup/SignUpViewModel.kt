package com.example.ddh.signup

import android.util.Log
import androidx.databinding.ObservableField
import com.example.ddh.data.repository.RepositoryImpl

class SignUpViewModel(
    private val repository: RepositoryImpl
) {
    val userInfoHashMap = HashMap<String, String>()
    var signUpResultUsername: String? = ""

    val startSignUpCompleteActivity = ObservableField<Unit>()
    val failToPostUserInfo = ObservableField<Unit>()

    val etEmail: String = ""

    //    fun postSignUpUserInfo(userInfoMainAcitivity: HashMap<String, String>) {
    fun postSignUpUserInfo() {
        /* 테스트 */
        userInfoHashMap["email"] = "asfs12@naver.com"
        userInfoHashMap["password"] = "badgood123"
        userInfoHashMap["name"] = "함도영"
        userInfoHashMap["birthday"] = "1995-02-28"
        userInfoHashMap["tel"] = "01041527671"
        userInfoHashMap["gender"] = "남자"
        userInfoHashMap["recommendedCode"] = ""

        if (userInfoHashMap.isEmpty()) {
        } else {
            repository.postSignUp(
                userInfoHashMap,
                success = {
                    it.run {
                        signUpResultUsername = it.data!!.email

                        if (!signUpResultUsername.isNullOrEmpty()) {
                            startSignUpCompleteActivity.notifyChange()
                        }
                    }
                    Log.d("POST-SignUP", "$signUpResultUsername")
                },
                fail = { failToPostUserInfo.notifyChange() }
            )
        }

    }


    fun signUpBtnClick() {
        checkAllEditTextValue()
        //postSignUpUserInfo()
    }

    private fun checkAllEditTextValue() {

    }
}