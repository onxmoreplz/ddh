package com.example.ddh.data.remote

import android.util.Log
import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.data.dto.SignUpUserData.SignUpUserResponse
import com.example.ddh.data.remote.api.SignUpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.HashMap

class UserRemoteDataSource {

    fun postSignUpRemote(
        userInfo: HashMap<String, String>,
        success: (SignUpUserResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val call = SignUpClient.service.postSignUp(userInfo)
        call.enqueue(object : Callback<SignUpUserResponse> {
            override fun onResponse(call: Call<SignUpUserResponse>, response: Response<SignUpUserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                    Log.d("UserResponseImpl", "${response.body()} response success")
                } else {
                    Log.e("UserResponseImpl", "통신은 성공했으나, 응답에 문제가 발생하였습니다.")
                }
            }

            override fun onFailure(call: Call<SignUpUserResponse>, t: Throwable) {
                fail(t)
            }

        })
    }

    fun postUserLoginRemote(
        loginHashMap: HashMap<String, String>,
        success: (SignUpUserData.LoginResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val call = SignUpClient.service.postLoginUser(loginHashMap)
        call.enqueue(object : Callback<SignUpUserData.LoginResponse> {
            override fun onResponse(call: Call<SignUpUserData.LoginResponse>, response: Response<SignUpUserData.LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                }
            }
            override fun onFailure(call: Call<SignUpUserData.LoginResponse>, t: Throwable) {
                fail(t)
            }
        })
    }

    fun postVerifyingCode(
        verifyingHashMap: HashMap<String, String>,
        success: (SignUpUserData.VerifyingResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val call = SignUpClient.service.postVerifyEmail(verifyingHashMap)
        call.enqueue(object : Callback<SignUpUserData.VerifyingResponse> {
            override fun onResponse(call: Call<SignUpUserData.VerifyingResponse>, response: Response<SignUpUserData.VerifyingResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    Log.d("UserRemoteDataSource", "Something goes wrong : ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<SignUpUserData.VerifyingResponse>, t: Throwable) {
                fail(t)
            }
        })
    }
}