package com.example.detailedregistration.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.detailedregistration.RegisterPreference
import com.example.detailedregistration.model.Users

class MainViewModel : ViewModel() {


    private var userList = MutableLiveData<ArrayList<Users>>()
    val testuser = MutableLiveData<String>()

    val userListData: LiveData<ArrayList<Users>>
        get() = userList


    init {
        userList.value = ArrayList()
        testuser.value = "Type Anything..."
    }

    fun addUserList(username: String, mobileNumber: String, email: String, password: String) {


        userList.value?.add(Users(username, mobileNumber, email, password))
        Log.i("User","${userListData.value}")

    }
    fun clearAll(){

        userList.value = ArrayList()
    }


}