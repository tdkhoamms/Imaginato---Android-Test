package com.example.room.mvvm.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.room.mvvm.R
import com.example.room.mvvm.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var context: Context
    private lateinit var strUsername: String
    private lateinit var strPassword: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this@MainActivity

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.getLoginDetails(context)!!.observe(this, Observer {
            if (it != null) {
                txtUsername.setText(it.Username)
                txtPassword.setText(it.Password)
            }
        })

        btnAddLogin.setOnClickListener {
            strUsername = txtUsername.text.toString().trim()
            strPassword = txtPassword.text.toString().trim()
            when {
                strPassword.isEmpty() -> {
                    txtUsername.error = "Please enter the username"
                }
                strPassword.isEmpty() -> {
                    txtPassword.error = "Please enter the username"
                }
                else -> {
                    onLogin(context, strUsername, strPassword)
                }
            }
        }


    }

    private fun onLogin(context: Context, strUsername: String, strPassword: String) {
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
                runOnUiThread {
                    lblInsertResponse.text = "Login Error"
                }
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val x_Acc = response.header("X-Acc") ?: ""
                val jsonData = response.body()!!.string()
                val obj = JSONObject(jsonData)
                val userObj = obj.getJSONObject("user")
                val userId = userObj.getString("userId")
                val userName = userObj.getString("userName")
                loginViewModel.insertData(context, userName, strPassword, userId, x_Acc)
                runOnUiThread {
                    lblInsertResponse.text = "Login Successfully"
                }
            }
        })
    }


}
