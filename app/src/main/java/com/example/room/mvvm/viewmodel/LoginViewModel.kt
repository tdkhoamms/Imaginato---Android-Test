package com.example.room.mvvm.viewmodel

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.repository.LoginRepository
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class LoginViewModel : ViewModel() {

    var liveDataLogin: LiveData<LoginTableModel>? = null

    fun insertData(
        context: Context,
        username: String,
        password: String,
        userId: String,
        x_acc: String
    ) {
        LoginRepository.insertData(context, username, password, userId, x_acc)
    }

    fun getLoginDetails(context: Context): LiveData<LoginTableModel>? {
        liveDataLogin = LoginRepository.getLoginDetails(context)
        return liveDataLogin
    }

    fun callApiLogin(
        context: Context,
        strUsername: String,
        strPassword: String,
        lblInsertResponse: TextView
    ) {
        val client = OkHttpClient()
        val url = "https://private-222d3-homework5.apiary-mock.com/api/login"
        val formBody = FormBody.Builder()
            .add("username", strUsername)
            .add("password", strPassword)
            .build()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("IMSI", "357175048449937")
            .addHeader("IMEI", "510110406068589")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                lblInsertResponse.text = "Login Error"
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val x_Acc = response.header("X-Acc") ?: ""
                val jsonData = response.body()!!.string()
                val obj = JSONObject(jsonData)
                val userObj = obj.getJSONObject("user")
                val userId = userObj.getString("userId")
                val userName = userObj.getString("userName")
                insertData(context, userName, strPassword, userId, x_Acc)
                lblInsertResponse.text = "Login Successfully"
            }
        })
    }

}
