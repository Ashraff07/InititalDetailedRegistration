package com.example.detailedregistration.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ScrollCaptureSession
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.detailedregistration.R
import com.example.detailedregistration.RegisterPreference
import com.example.detailedregistration.adapter.UsersAdapter
import com.example.detailedregistration.databinding.FragmentDashboardBinding
import com.example.detailedregistration.model.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    var userList = ArrayList<Users>()
    lateinit var userRVAdapter: UsersAdapter

    private lateinit var dash_username: TextView
    private lateinit var dash_mobileNumber: TextView
    private lateinit var dash_email: TextView
    private lateinit var dash_logout: Button

    lateinit var session: RegisterPreference
    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)

        session = RegisterPreference(this.requireContext())


        binding.btnClear.setOnClickListener {
            session.logOutUser()
        }


//        dash_username = binding.dashUsername
//        dash_mobileNumber = binding.dashMobilenumber
//        dash_email = binding.dashEmail
//        dash_logout = binding.btnLogout


        // session.checkRegistration()

        val user: HashMap<String, String> = session.getUserDetails()

        val username = user.get(RegisterPreference.KEY_USERNAME)
        val mobileNumber = user.get(RegisterPreference.KEY_MOBILE)
        val email = user.get(RegisterPreference.KEY_EMAIL)
        val password = user.get(RegisterPreference.KEY_PASSWORD)

        val json = user.get(RegisterPreference.KEY_USERS)
        val type: Type = object : TypeToken<ArrayList<Users>>() {}.type
        userList = gson.fromJson<Any>(json, type) as ArrayList<Users>

        val ulist2: ArrayList<Users> = userList




        Toast.makeText(this.requireContext(), "Retriving List Sucessful. ", Toast.LENGTH_SHORT)
            .show()




        prepareRecyclerView()

        userRVAdapter.setUsersList(userList)


//        dash_username.setText(username)
//        dash_mobileNumber.setText(mobileNumber)
//        dash_email.setText(email)
//
//        dash_logout.setOnClickListener{
//           session.logOutUser()
//            view?.findNavController()?.navigate(R.id.action_dashboardFragment_to_registrationFragment)
//
//        }


        return binding.root


    }


    private fun prepareRecyclerView() {
        userRVAdapter = UsersAdapter { position ->
            onListItemClick(position)
        }
        binding.rvUsername.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = userRVAdapter

        }
    }


    private fun onListItemClick(position: Int) {

        val username = userList[position].username
        val mobile = userList[position].mobileNumber
        val email = userList[position].email

        val bundle = bundleOf("username" to username, "mobile" to mobile, "email" to email)
        view?.findNavController()
            ?.navigate(R.id.action_dashboardFragment_to_detailedFragment, bundle)

    }


}