package com.example.room.mvvm.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.room.mvvm.model.LoginTableModel

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(loginTableModel: LoginTableModel)

    @Query("DELETE FROM Login")
    fun deleteAllData()

    @Query("SELECT * FROM Login")
    fun getLoginDetails(): LiveData<LoginTableModel>

}
