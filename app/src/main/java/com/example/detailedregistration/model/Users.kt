package com.example.detailedregistration.model

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var username:String,
    val mobileNumber:String,
    val email:String,
    val password:String
):Parcelable
