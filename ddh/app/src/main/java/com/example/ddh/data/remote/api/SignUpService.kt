package com.example.ddh.data.remote.api

import com.example.ddh.data.dto.SignUpUserData
import retrofit2.Call
import retrofit2.http.*

interface SignUpService {

    @FormUrlEncoded // x-www-urlencoded로 보내겠다.
    @POST("user/register")
    fun postSignUp(
        @FieldMap params: HashMap<String, String>
    ): Call<SignUpUserData.SignUpUserResponse>

    @FormUrlEncoded // x-www-urlencoded로 보내겠다.
    @POST("user/login")
    fun postLoginUser(
        @FieldMap params: HashMap<String, String>
    ): Call<SignUpUserData.LoginResponse>

    @FormUrlEncoded
    @POST("user/emailCheck")
    fun postVerifyEmail(
        @FieldMap params: HashMap<String, String>
    ): Call<SignUpUserData.VerifyingResponse>
}