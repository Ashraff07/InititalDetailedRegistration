package com.example.detailedregistration.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.detailedregistration.R
import com.example.detailedregistration.RegisterPreference
import com.example.detailedregistration.adapter.UsersAdapter
import com.example.detailedregistration.databinding.FragmentDashboardBinding
import com.example.detailedregistration.model.Users
import com.example.detailedregistration.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    var userList = ArrayList<Users>()
    lateinit var userRVAdapter: UsersAdapter
    lateinit var session: RegisterPreference
    lateinit var viewModel: MainViewModel
    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)

        session = RegisterPreference(this.requireContext())
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]


        binding.btnClear.setOnClickListener {
            session.logOutUser()
            viewModel.clearAll()

        }



        if (session.isRegistered()) {
            val user: HashMap<String, String> = session.getUserDetails()
            val json = user.get(RegisterPreference.KEY_USERS)
            val type: Type = object : TypeToken<ArrayList<Users>>() {}.type
            userList = gson.fromJson<Any>(json, type) as ArrayList<Users>
        }


        Toast.makeText(this.requireContext(), "Retriving List Sucessful. ", Toast.LENGTH_SHORT)
            .show()

        prepareRecyclerView()

        userRVAdapter.setUsersList(userList)

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