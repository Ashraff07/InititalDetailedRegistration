package com.example.detailedregistration.view

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.findNavController
import com.example.detailedregistration.R
import com.example.detailedregistration.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding

    private lateinit var username: EditText
    private lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        username = binding.logUsername
        password = binding.logPassword

        binding.btnRegister.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.btnLogin.setOnClickListener {
            validateLogin()
        }

        return binding.root
    }

    private fun validateLogin() {
        val icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_error)

        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please enter Username!", icon)
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please enter Password!", icon)
            }


            username.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() -> {
                if (username.text.toString().matches(Regex("^[A-Za-z][A-Za-z0-9_]{7,29}$"))) {

                    Toast.makeText(context,"Logged-in Successfully",Toast.LENGTH_LONG).show()

                } else {
                    username.setError("Enter a valid Username",icon)
                }

            }


        }

    }
}

