package com.example.ddh.data.repository

import com.example.ddh.data.dto.PartyData
import com.example.ddh.data.dto.SignUpUserData

interface Repository {

    fun getVerfyEmail(
        email: String,
        success: (SignUpUserData.VerifyEmailResponse) -> Unit,
        fail: (Throwable) -> Unit
    )

    fun getNicknameCheck(
        nickname: String,
        success: (SignUpUserData.CheckNicknameResponse) -> Unit,
        fail: (Throwable) -> Unit
    )

    fun postSignUp(
        userInfo: HashMap<String, String>,
        success: (SignUpUserData.SignUpUserResponse) -> Unit,
        fail: (Throwable) -> Unit
    )

    fun postLogin(
        hashMapLogin: HashMap<String, String>,
        success: (SignUpUserData.LoginResponse) -> Unit,
        fail: (Throwable) -> Unit
    )

    fun postUploadParty(
        hashMapParty: HashMap<String, Any>,
        success: (PartyData.CreatePartyResponse) -> Unit,
        fail: (Throwable) -> Unit
    )
}