package com.example.ddh.data.remote

import com.example.ddh.data.dto.PartyData
import com.example.ddh.data.remote.api.SignUpClient
import com.example.ddh.data.remote.api.SignUpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartyRemoteDataSource {
    private val service = SignUpService.create()

    fun postUploadParty(
        hashMapLogin: HashMap<String, Any>,
        success: (PartyData.CreatePartyResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val call = service.postUploadParty(hashMapLogin)
        call.enqueue(object : Callback<PartyData.CreatePartyResponse> {
            override fun onResponse(call: Call<PartyData.CreatePartyResponse>, response: Response<PartyData.CreatePartyResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                }
            }
            override fun onFailure(call: Call<PartyData.CreatePartyResponse>, t: Throwable) {
                fail(t)
            }
        })
    }
}
