package com.example.room.mvvm.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.room.mvvm.R
import com.example.room.mvvm.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*

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
            if (strPassword.isEmpty()) {
                txtUsername.error = "Please enter the username"
            } else if (strPassword.isEmpty()) {
                txtPassword.error = "Please enter the username"
            } else {
                loginViewModel.callApiLogin(context, strUsername, strPassword, lblInsertResponse)
            }
        }
    }


}
