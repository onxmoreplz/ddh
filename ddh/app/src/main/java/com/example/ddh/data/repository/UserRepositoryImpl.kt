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

    override fun postLogin(
        loginHashMap: HashMap<String, String>,
        success: (SignUpUserData.LoginResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        userRemoteDataSource.postUserLoginRemote(loginHashMap, success, fail)
    }

    override fun postVerfyEmail(
        verifyHashmap: HashMap<String, String>,
        success: (SignUpUserData.VerifyingResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        userRemoteDataSource.postVerifyingCode(verifyHashmap, success, fail)
    }

}