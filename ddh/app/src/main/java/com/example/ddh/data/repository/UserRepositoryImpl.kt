package com.example.ddh.data.repository

import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.data.remote.UserRemoteDataSource

class UserRepositoryImpl : UserRepository {
    private val userRemoteDataSource = UserRemoteDataSource()

    override fun getVerfyEmail(
        email: String,
        success: (SignUpUserData.VerifyEmailResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        userRemoteDataSource.getVerifyingEmail(email, success, fail)
    }

    override fun getNicknameCheck(
        nickname: String,
        success: (SignUpUserData.CheckNicknameResponse) -> Unit,
        fail: (Throwable) -> Unit)
    {
        userRemoteDataSource.getCheckNickname(nickname, success, fail)
    }

    override fun postSignUp(
        userInfo: HashMap<String, String>,
        success: (SignUpUserData.SignUpUserResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        userRemoteDataSource.postSignUpRemote(userInfo, success, fail)
    }

    override fun postLogin(
        hashMapLogin: HashMap<String, String>,
        success: (SignUpUserData.LoginResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        userRemoteDataSource.postUserLoginRemote(hashMapLogin, success, fail)
    }


}