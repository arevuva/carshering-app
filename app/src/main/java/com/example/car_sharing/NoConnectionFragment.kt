package com.example.car_sharing

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.car_sharing.databinding.FragmentNoConnectionBinding

class NoConnectionFragment : Fragment() {
    private var _binding: FragmentNoConnectionBinding? = null
    private val binding get() = _binding!!

    var isValid = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentNoConnectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.retryButton.setOnClickListener{
            findNavController().navigate(R.id.action_noConnectionFragment_to_splashScreenFragment)
        }
    }



}