package com.example.detailedregistration.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.detailedregistration.R
import com.example.detailedregistration.databinding.FragmentDetailedBinding
import com.example.detailedregistration.databinding.FragmentTestBinding
import com.example.detailedregistration.viewmodel.MainViewModel

class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       // binding = DataBindingUtil.setContentView(requireActivity(),R.layout.fragment_test)
        binding = FragmentTestBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.myViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root

    }


}