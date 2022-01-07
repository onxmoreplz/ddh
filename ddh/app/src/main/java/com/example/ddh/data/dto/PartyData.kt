package com.example.ddh.data.dto

class PartyData {
    data class CreateParty(
        val code: Int? = null,
        val data: Party? = null
    )
    data class Party(
        val creator: Int? = null,
        val title: String? = null,
        val description: String? = null,
        val mountain: String? = null,
        val place: String? = null,
        val gender: String? = null,
        val cost: Int? = null,
        val costDescription: String? = null,
        val maximum: Int? = null,
        val minimum: String? = null,
        val departureAt: String? = null,
        val participants: ArrayList<SignUpUserData.User>? = null
    )
}