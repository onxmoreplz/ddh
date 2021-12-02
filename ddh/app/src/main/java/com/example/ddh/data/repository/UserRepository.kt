package com.example.ddh.data.repository

import com.example.ddh.data.dto.SignUpUserData

interface UserRepository {

    fun postSignUp(
        userInfo: HashMap<String, String>,
        success: (SignUpUserData.SignUpUserResponse) -> Unit,
        fail: (Throwable) -> Unit
    )

    fun postLogin(
        loginHashMap: HashMap<String, String>,
        success: (SignUpUserData.LoginResponse) -> Unit,
        fail: (Throwable) -> Unit
    )

    fun postVerfyEmail(
        verifyHashmap: HashMap<String, String>,
        success: (SignUpUserData.VerifyingResponse) -> Unit,
        fail: (Throwable) -> Unit
    )

}