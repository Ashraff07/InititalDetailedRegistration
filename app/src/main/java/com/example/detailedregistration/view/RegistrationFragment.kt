package com.example.detailedregistration.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.findNavController
import com.example.detailedregistration.R
import com.example.detailedregistration.RegisterPreference
import com.example.detailedregistration.adapter.UsersAdapter
import com.example.detailedregistration.databinding.FragmentLoginBinding
import com.example.detailedregistration.databinding.FragmentRegistrationBinding
import com.example.detailedregistration.model.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.crypto.AEADBadTagException


class RegistrationFragment : Fragment() {


    private lateinit var binding: FragmentRegistrationBinding

    private lateinit var username: EditText
    private lateinit var mobileNumber: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var btnRegister: Button

    lateinit var session: RegisterPreference
    var ulist = ArrayList<Users>()
    var gson = Gson()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(layoutInflater)

        username = binding.regUsername
        mobileNumber = binding.regMobilenumber
        email = binding.regEmail
        password = binding.regPassword
        confirmPassword = binding.regConfirmPassword
        btnRegister = binding.btnRegister

        session = RegisterPreference(this.requireContext())

        binding.btnLogin.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {


            val dashUsername = username.text.toString().trim()
            val dashMobileNumber = mobileNumber.text.toString().trim()
            val dashEmail = email.text.toString().trim()
            val dashPassword = password.text.toString().trim()

            if (dashUsername.isEmpty() && dashMobileNumber.isEmpty() && dashEmail.isEmpty()) {
                Toast.makeText(this.requireContext(), "Registration Failed", Toast.LENGTH_LONG)
                    .show()
            } else {

                if (validateRegistration()) {
                    if (session.isRegistered()) {

                        val user: HashMap<String, String> = session.getUserDetails()
                        val json = user.get(RegisterPreference.KEY_USERS)
                        val type: Type = object : TypeToken<ArrayList<Users>>() {}.type
                        ulist = gson.fromJson<Any>(json, type) as ArrayList<Users>
                    }

                    ulist.add(Users(dashUsername, dashMobileNumber, dashEmail, dashPassword))
                    session.createRegistrationSession(ulist)


                    // view?.findNavController()?.navigate(R.id.action_registrationFragment_to_dashboardFragment)

                }

            }


        }

        return binding.root
    }

    private fun validateRegistration(): Boolean {
        val icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_error)

        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please enter Username!", icon)
            }
            TextUtils.isEmpty(mobileNumber.text.toString().trim()) -> {
                mobileNumber.setError("Please enter Mobile Number!", icon)
            }
            TextUtils.isEmpty(email.text.toString().trim()) -> {
                email.setError("Please enter Email Id!", icon)
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please enter Password!", icon)
            }
            TextUtils.isEmpty(confirmPassword.text.toString().trim()) -> {
                confirmPassword.setError("Please enter the password again!", icon)
            }


            username.text.toString().isNotEmpty() &&
                    mobileNumber.text.toString().isNotEmpty() &&
                    email.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() &&
                    confirmPassword.text.toString().isNotEmpty() -> {

                if (session.isRegistered()) {

                    val user: HashMap<String, String> = session.getUserDetails()
                    val json = user.get(RegisterPreference.KEY_USERS)
                    val type: Type = object : TypeToken<ArrayList<Users>>() {}.type
                    ulist = gson.fromJson<Any>(json, type) as ArrayList<Users>
                }

                for (i in ulist) {
                    if (i.username.toString().equals(username.text.toString())) {
                        username.setError("UserName already exists!!", icon)
                        return false
                    } else if (i.mobileNumber.toString().equals(mobileNumber.text.toString())) {
                        mobileNumber.setError("Mobile Number already exists!! try a new one", icon)
                        return false
                    } else if (i.email.toString().equals(email.text.toString())) {
                        email.setError("Email id already exits!!", icon)
                        return false
                    }

                }
                if (username.text.toString().matches(Regex("^[A-Za-z][A-Za-z0-9_]{7,29}$"))) {
                    if (mobileNumber.text.toString().length == 10 && mobileNumber.text.toString()
                            .matches(Regex("[6-9][0-9]{9}"))
                    ) {
                        if (email.text.toString()
                                .matches((Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")))
                        ) {
                            if (password.text.toString().length >= 8) {
                                if (password.text.toString() == confirmPassword.text.toString()) {
                                    Toast.makeText(
                                        context,
                                        "Registration Successful!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return true
                                } else {
                                    confirmPassword.setError("Oops that didn't match!!", icon)
                                }

                            } else {
                                password.setError("Enter a password greater than 8 chars", icon)
                            }

                        } else {
                            email.setError("Please enter a Valid Email ID!!", icon)
                        }

                    } else {
                        mobileNumber.setError("Please enter a valid Mobile Number", icon)
                    }

                } else {
                    username.setError("Please enter a Valid UserName!!", icon)
                }

            }


        }
        return false
    }

}