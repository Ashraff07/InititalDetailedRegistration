package com.example.detailedregistration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.example.detailedregistration.model.Users
import com.example.detailedregistration.view.RegistrationFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RegisterPreference(var con: Context) {

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    val gson = Gson()


    var PRIVATEMODE: Int = 0

    init {
        pref = con.getSharedPreferences(PREF_NAME, PRIVATEMODE)
        editor = pref.edit()
    }

    companion object {
        val PREF_NAME = "Register_Preferences"
        val IS_REGISTER = "isRegistered"
        val KEY_USERS = "users"
    }

    fun createRegistrationSession(
        ulist: ArrayList<Users>
    ) {
        editor.putBoolean(IS_REGISTER, true)

        val json = gson.toJson(ulist)
        editor.putString(KEY_USERS, json)

        editor.commit()

    }

//    fun checkRegistration() {
//        if (!this.isRegistered()) {
//            val i: Intent = Intent(con, RegistrationFragment::class.java)
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            con.startActivity(i)
//        }
//    }

    fun getUserDetails(): HashMap<String, String> {
        val user: Map<String, String> = HashMap<String, String>()

        (user as HashMap).put(KEY_USERS, pref.getString(KEY_USERS, null)!!)
        return user
    }

    fun logOutUser() {
        editor.clear()
        editor.commit()

    }

    fun isRegistered(): Boolean {

        return pref.getBoolean(IS_REGISTER, false)
    }
}