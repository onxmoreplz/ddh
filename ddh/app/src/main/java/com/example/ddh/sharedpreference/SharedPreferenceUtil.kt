package com.example.ddh.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray

class SharedPreferenceUtil(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    var arrayListUser = ArrayList<String>()

  /*  fun setUserInfo(
        email: String,
        name: String,
        nickname: String,
        tel: String,
        accessToken: String,
        refreshToken: String,
        message: String
    ) {
        arrayListUser.add(email); arrayListUser.add(name); arrayListUser.add(nickname); arrayListUser.add(tel); arrayListUser.add(accessToken); arrayListUser.add(refreshToken); arrayListUser.add(message);
        editor.putString("user_info", JSONArray(arrayListUser).toString()).apply()
    }*/

    // 사용자 이메일
    fun setUserEmail(email: String) {
        editor.putString("user_email", email).apply()
    }
    fun setUserPassword(password: String) {
        editor.putString("user_password", password).apply()
    }
    fun setUserName(email: String) {
        editor.putString("user_name", email).apply()
    }
    fun setUserNickname(email: String) {
        editor.putString("user_nickname", email).apply()
    }
    fun setUserTel(email: String) {
        editor.putString("user_tel", email).apply()
    }
    fun setUserAccessToken(email: String) {
        editor.putString("user_access_token", email).apply()
    }
    fun setUserRefreshToken(email: String) {
        editor.putString("user_refresh_token", email).apply()
    }
    fun setCheckBoxLoginInfo(checked: Boolean) {
        editor.putBoolean("check_box_login_info", checked).apply()
    }

    fun getUserEmail(): String? = sharedPreferences.getString("user_email", null)
    fun getUserPassword(): String? = sharedPreferences.getString("user_password", null)
    fun getUserName(): String? = sharedPreferences.getString("user_name", null)
    fun getUserNickname(): String? = sharedPreferences.getString("user_nickname", null)
    fun getUserTel(): String? = sharedPreferences.getString("user_tel", null)
    fun getUserAccessToken(): String? = sharedPreferences.getString("user_access_token", null)
    fun getUserRefreshToken(): String? = sharedPreferences.getString("user_refresh_token", null)
    fun getCheckBoxLoginInfo(): Boolean? = sharedPreferences.getBoolean("check_box_login_info", false)

}