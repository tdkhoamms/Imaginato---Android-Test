package com.example.room.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.repository.LoginRepository


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

}
