package com.example.ddh.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.data.repository.RepositoryImpl

class LoginViewModel(
    private val repository: RepositoryImpl
) {
    private val hashMapLogin = HashMap<String, String>()
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    val email: LiveData<String> get() = _email
    val password: LiveData<String> get() = _password
    var loginUser = SignUpUserData.User()

    private var isValidEmail: Boolean = false
    val emailChecking = ObservableField<Unit>()
    val successLogin = ObservableField<Unit>()
    val failLogin = ObservableField<Unit>()

    fun getUserLoginInfo() {
        hashMapLogin["email"] = email.toString()!!
        hashMapLogin["password"] = password.toString()!!
        repository.postLogin(
            hashMapLogin,
            success = {
                it.run {
                    when (code) {
                        0 -> { // 로그인 성공
                            //loginUser = it.data!!
                            successLogin.notifyChange()
                        }
                        1001 -> print(33)
                        1002 -> print(22)
                        1003 -> print(23)
                    }
                }
            }
        ) {}
    }

}