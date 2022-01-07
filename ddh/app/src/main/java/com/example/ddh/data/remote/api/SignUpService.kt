package com.example.ddh.data.remote.api

import com.example.ddh.data.dto.SignUpUserData
import retrofit2.Call
import retrofit2.http.*

interface SignUpService {

    @GET("user/email")
    fun getVerifyEmail(
        @Query("email") email: String,
    ): Call<SignUpUserData.VerifyEmailResponse>

    @GET("user/nickName")
    fun getCheckNickname(
        @Query("nickName") nickName: String,
    ): Call<SignUpUserData.CheckNicknameResponse>

    @FormUrlEncoded // x-www-urlencoded로 보내겠다.
    @POST("user")
    fun postSignUp(
        @FieldMap params: HashMap<String, String>
    ): Call<SignUpUserData.SignUpUserResponse>


    @FormUrlEncoded // x-www-urlencoded로 보내겠다.
    @POST("user/login")
    fun postLogin(
        @FieldMap params: HashMap<String, String>
    ): Call<SignUpUserData.LoginResponse>

}