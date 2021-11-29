package com.example.ddh.data.repository

import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.data.remote.UserRemoteDataSource

class UserRepositoryImpl : UserRepository {
    private val userRemoteDataSource =  UserRemoteDataSource()

    override fun postSignUp(
        userInfo: HashMap<String, String>,
        success: (SignUpUserData.SignUpUserResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        userRemoteDataSource.postSignUpRemote(userInfo, success, fail)
    }

    override fun getLogin(
        email: String,
        password: String,
        success: (SignUpUserData.LoginResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        userRemoteDataSource.getUserLoginRemote(email, password, success, fail)
    }

}