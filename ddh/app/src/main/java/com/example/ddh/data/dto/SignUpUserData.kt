package com.example.ddh.data.dto

import android.app.Notification
import java.util.*

class SignUpUserData {

    data class VerifyEmailResponse(
        val code: Int? = null,
        val data: VerifyData? = null
    )

    data class CheckNicknameResponse(
        val code: Int? = null,
        val data: Nickname? = null
    )

    data class SignUpUserResponse(
        val data: PostSignUpUser? = null,
        val code: Int? = null
    )

    data class LoginResponse(
        val code: Int? = null,
        val data: LoginData? = null
    )

// ---------------------------------------------------------------------------------------

    data class VerifyData(
        val email: String? = null,
        val message: String? = null
    )

    data class Nickname (
        val nickName: String? = null,
        val message: String? = null
    )

    data class User(
        val email: String? = null,
        val password: String? = null,
        val name: String? = null,
        val nickName: String? = null,
        val tel: String? = null,
        val notification: String? = null,
        val vaccineStatus: String? = null,
        val birthday: Date? = null, // Date형
        val gender: String? = null,
        val profilePicUrl: String? = null
    )

    data class PostSignUpUser (
        val email: String? = null,
        val nickName: String? = null
    )

    data class LoginData (
        val email: String? = null,
        val name: String? = null,
        val nickName: String? = null,
        val tel: String? = null,
        val accessToken: String? = null,
        val refreshToken: String? = null,
        val message: String? = null,
    )
}