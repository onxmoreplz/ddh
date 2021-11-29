package com.example.ddh.data.remote

import android.util.Log
import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.data.dto.SignUpUserData.SignUpUserResponse
import com.example.ddh.data.remote.api.SignUpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

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
}