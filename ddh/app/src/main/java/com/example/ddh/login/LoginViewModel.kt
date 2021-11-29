package com.example.ddh.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.data.repository.UserRepositoryImpl

class LoginViewModel(
    private val userRepository: UserRepositoryImpl
) {
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    val email: LiveData<String> get() = _email
    val password: LiveData<String> get() = _password
    var loginUser = SignUpUserData.User()

    fun getUserLoginInfo() {
        userRepository.getLogin(
            email.toString(),
            password.toString(),
            success = {
                it.run {
                    loginUser = it.data!!
                }
            },
            fail = {}

        )
    }
}