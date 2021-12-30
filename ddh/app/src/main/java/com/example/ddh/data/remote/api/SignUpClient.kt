package com.example.ddh.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 서버 호출이 필요할 때마다 인터페이스를 구현하는 것은 비효율적
 *  -> Client는 싱글턴(Object)으로 구현
 * */

object SignUpClient {
    private const val baseUrl = "https://oleunaelim-server.herokuapp.com/api/" //Base Url 주소 끝은 항상 '/'으로 끝나야 함

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: SignUpService = retrofit.create(SignUpService::class.java)
}