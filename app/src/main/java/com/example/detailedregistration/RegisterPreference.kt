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

    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var ulist = ArrayList<Users>()
    val gson = Gson()


    var PRIVATEMODE: Int = 0

    init {
        pref = con.getSharedPreferences(PREF_NAME, PRIVATEMODE)
        editor = pref.edit()


        val json = pref.getString("users", null)
        val type: Type = object : TypeToken<ArrayList<Users>>() {}.type
//       ulist = gson.fromJson<Any>(json, type) as ArrayList<Users>


    }

    companion object {
        val PREF_NAME = "Register_Preferences"
        val IS_REGISTER = "isRegistered"
        val KEY_USERNAME = "username"
        val KEY_MOBILE = "mobileNumber"
        val KEY_EMAIL = "email"
        val KEY_PASSWORD = "password"
        val KEY_USERS = "users"
    }

    fun createRegistrationSession(
        username: String,
        mobileNumber: String,
        email: String,
        password: String,
        ulist: ArrayList<Users>
    ) {
        editor.putBoolean(IS_REGISTER, true)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_MOBILE, mobileNumber)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)

        val json = gson.toJson(ulist)
        editor.putString(KEY_USERS, json)

        editor.apply()
//        editor.commit()

//        Toast.makeText(this.con, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT)
//            .show()
    }

    fun checkRegistration() {
        if (!this.isRegistered()) {
            val i: Intent = Intent(con, RegistrationFragment::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(i)
        }
    }

    fun getUserDetails(): HashMap<String, String> {
        val user: Map<String, String> = HashMap<String, String>()

        (user as HashMap).put(KEY_USERNAME, pref.getString(KEY_USERNAME, null)!!)
        (user as HashMap).put(KEY_MOBILE, pref.getString(KEY_MOBILE, null)!!)
        (user as HashMap).put(KEY_EMAIL, pref.getString(KEY_EMAIL, null)!!)
        (user as HashMap).put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null)!!)
        (user as HashMap).put(KEY_USERS, pref.getString(KEY_USERS, null)!!)
        return user
    }

    fun logOutUser() {
        editor.clear()
        editor.commit()
//        var i :Intent = Intent(con,RegistrationFragment::class.java)
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        con.startActivity(i)
    }

    fun isRegistered(): Boolean {

        return pref.getBoolean(IS_REGISTER, false)
    }
}