package com.example.ddh.data.dto

import java.util.*

class SignUpUserData {
    data class SignUpUserResponse(
        val items: List<User>,
        val lastBuildDate: String,
        val start: Int,
        val total: Int,
    )

    data class User(
        val email: String,
        val password: String,
        val name: String,
        val phone: String,
        val birthday: Date, // Date형
        val gender: String,
        val personalInformation: String
    )
}