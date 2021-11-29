package com.example.ddh.data.repository

import com.example.ddh.data.dto.SignUpUserData

interface UserRepository {

    fun postSignUp(
        userInfo: HashMap<String, String>,
        success: (SignUpUserData.SignUpUserResponse) -> Unit,
        fail: (Throwable) -> Unit
    )
}