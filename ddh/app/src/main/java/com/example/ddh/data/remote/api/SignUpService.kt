package com.example.ddh.data.remote.api

import com.example.ddh.App
import com.example.ddh.data.dto.PartyData
import com.example.ddh.data.dto.SignUpUserData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.io.IOException

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

    @FormUrlEncoded // x-www-urlencoded로 보내겠다.
    @POST("post")
    fun postUploadParty(
        @FieldMap params: HashMap<String, Any>
    ): Call<PartyData.CreatePartyResponse>

    companion object {
        private const val BASE_URL = "https://oleunaelim-server.herokuapp.com/api/"

        fun create(): SignUpService {

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed(request)
            }

            val gson: Gson = GsonBuilder()
                .setLenient()
                .create()

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(HeaderInterceptor(App.sharedPrefs.getUserAccessToken().toString()))
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(SignUpService::class.java)
        }

        class HeaderInterceptor constructor(private val token: String) : Interceptor {

            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val token = "Bearer $token"
                val newRequest = chain.request().newBuilder().addHeader("Authorization", token).build()
                return chain.proceed(newRequest)
            }
        }
    }

}