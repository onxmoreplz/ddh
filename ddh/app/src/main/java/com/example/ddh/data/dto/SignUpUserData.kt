package com.example.ddh.data.dto

import java.util.*

class SignUpUserData {
    data class SignUpUserResponse(
        val data: PostResultUser? = null,
        val code: Int? = null
    )

    data class LoginResponse(
        val code: Int? = null,
        val data: LoginData? = null
    )

    data class VerifyingResponse(
        val code: Int? = null,
        val data: VerifyData? = null
    )

    data class VerifyData(
        val email: String? = null,
        val message: String? = null,
        val code: String? = null
    )

    data class User(
        val email: String? = null,
        val password: String? = null,
        val name: String? = null,
        val phone: String? = null,
        val birthday: Date? = null, // Date형
        val gender: String? = null,
        val personalInformation: String? = null
    )

    data class PostResultUser (
        val userId: String? = null,
        val name: String? = null,
        val message: String? = null
    )

    data class LoginData (
        val message: String? = null,
        val userId: String? = null,
        val name: String? = null,
        val accessToken: String? = null

    )
}