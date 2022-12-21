package com.example.detailedregistration.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.detailedregistration.R
import com.example.detailedregistration.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {


   private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)

        
        return binding.root

    }


}