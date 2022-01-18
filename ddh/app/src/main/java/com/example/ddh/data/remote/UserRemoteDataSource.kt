package com.example.ddh.data.remote

import android.util.Log
import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.data.dto.SignUpUserData.SignUpUserResponse
import com.example.ddh.data.remote.api.SignUpClient
import com.example.ddh.data.remote.api.SignUpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.HashMap

class UserRemoteDataSource {

    private val service = SignUpService.create()

    fun getVerifyingEmail(
        email: String,
        success: (SignUpUserData.VerifyEmailResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val call = service.getVerifyEmail(email)
        call.enqueue(object : Callback<SignUpUserData.VerifyEmailResponse> {
            override fun onResponse(call: Call<SignUpUserData.VerifyEmailResponse>, response: Response<SignUpUserData.VerifyEmailResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    Log.d("UserRemoteDataSource", "Something goes wrong : ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<SignUpUserData.VerifyEmailResponse>, t: Throwable) {
                fail(t)
            }
        })
    }

    fun getCheckNickname(
        nickname: String,
        success: (SignUpUserData.CheckNicknameResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val call = service.getCheckNickname(nickname)
        call.enqueue(object : Callback<SignUpUserData.CheckNicknameResponse> {
            override fun onResponse(call: Call<SignUpUserData.CheckNicknameResponse>, response: Response<SignUpUserData.CheckNicknameResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    Log.d("UserRemoteDataSource", "Something goes wrong : ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<SignUpUserData.CheckNicknameResponse>, t: Throwable) {
                fail(t)
            }
        })
    }

    fun postSignUpRemote(
        userInfo: HashMap<String, String>,
        success: (SignUpUserResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val call = service.postSignUp(userInfo)
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
        hashMapLogin: HashMap<String, String>,
        success: (SignUpUserData.LoginResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val call = service.postLogin(hashMapLogin)
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


}