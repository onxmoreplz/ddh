package com.example.ddh.data.dto

import java.util.*

class SignUpUserData {
    data class SignUpUserResponse(
        val data: PostResultUser? = null,
        val code: Int? = null
    )

    data class LoginResponse(
        val code: Int? = null,
        val data: User? = null
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
}