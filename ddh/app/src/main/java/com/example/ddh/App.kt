package com.example.ddh

import android.app.Application
import com.example.ddh.sharedpreference.SharedPreferenceUtil
import com.kakao.sdk.common.KakaoSdk

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
        sharedPrefs = SharedPreferenceUtil(applicationContext)

        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }

    companion object {
        lateinit var instance : App
            private set
        lateinit var sharedPrefs: SharedPreferenceUtil
    }
}