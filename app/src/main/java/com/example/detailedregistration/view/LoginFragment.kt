package com.example.detailedregistration.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.detailedregistration.R
import com.example.detailedregistration.RegisterPreference
import com.example.detailedregistration.databinding.FragmentLoginBinding
import com.example.detailedregistration.model.Users
import com.example.detailedregistration.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding

    private lateinit var username: EditText
    private lateinit var password: EditText

    lateinit var session: RegisterPreference
    lateinit var viewModel: MainViewModel

    val gson = Gson()
    var ulist = ArrayList<Users>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        username = binding.logUsername
        password = binding.logPassword

        session = RegisterPreference(this.requireContext())
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]


        binding.btnRegister.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.btnLogin.setOnClickListener {
            if (validateLogin()) {

                view?.findNavController()?.navigate(R.id.action_loginFragment_to_dashboardFragment3)
            }
        }

        binding.btnTest.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_testFragment)
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userListData.observe(viewLifecycleOwner) {
            ulist = it

        }
    }

    private fun validateLogin(): Boolean {
        val icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_error)

        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)

        if (session.isRegistered()) {
            val user: HashMap<String, String> = session.getUserDetails()
            val json = user.get(RegisterPreference.KEY_USERS)
            val type: Type = object : TypeToken<ArrayList<Users>>() {}.type
            ulist = gson.fromJson<Any>(json, type) as ArrayList<Users>
//            ulist = viewModel.userListData.value!!

        }


        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please enter Username!", icon)
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please enter Password!", icon)
            }


            username.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() -> {

                for (i in ulist) {
                    if (i.username.toString().equals(username.text.toString())) {
                        if (i.password.toString().equals(password.text.toString())) {
                            return true
                        } else {
                            password.setError("Please enter correct password", icon)
                        }
                    } else {
                        username.setError("User name doesn't exist!! Please Register", icon)
                    }
                }
            }
        }

        username.setError("User not yet registered!! please register",icon)
        return false

    }


}

