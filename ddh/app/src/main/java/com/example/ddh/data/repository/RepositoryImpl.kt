package com.example.ddh.data.repository

import com.example.ddh.data.dto.PartyData
import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.data.remote.PartyRemoteDataSource
import com.example.ddh.data.remote.UserRemoteDataSource

class RepositoryImpl : Repository {
    private val userRemoteDataSource = UserRemoteDataSource()
    private val partyRemoteDataSource = PartyRemoteDataSource()

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
        fail: (Throwable) -> Unit
    ) {
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

    override fun postUploadParty(
        hashMapParty: HashMap<String, Any>,
        success: (PartyData.CreatePartyResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        partyRemoteDataSource.postUploadParty(hashMapParty, success, fail)
    }


}