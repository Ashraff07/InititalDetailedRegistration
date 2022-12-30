package com.example.detailedregistration.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.detailedregistration.R
import com.example.detailedregistration.databinding.FragmentDetailedBinding

class DetailedFragment : Fragment() {

    private lateinit var binding: FragmentDetailedBinding
    private var username: Bundle? = null
    private var mobile: Bundle? = null
    private var email:Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            username = bundle.getBundle("username")
            mobile = bundle.getBundle("mobile")
            email = bundle.getBundle("email")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedBinding.inflate(layoutInflater)

        binding.btnLogout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_detailedFragment_to_loginFragment)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detUsername.text = arguments?.getString("username")
        binding.detMobile.text = arguments?.getString("mobile")
        binding.detEmail.text = arguments?.getString("email")
    }




}