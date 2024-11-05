package com.example.car_sharing.presenter.register_list.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.car_sharing.R
import com.example.car_sharing.databinding.FragmentPreviewRegBinding

class PreviewRegFragment : Fragment() {
    private var _binding: FragmentPreviewRegBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentPreviewRegBinding.inflate(inflater, container, false)
        binding.GoAuthButton.setOnClickListener {
            findNavController().navigate(R.id.action_previewRegFragment_to_mainRegFragment)
        }
        binding.GoRegButton.setOnClickListener {
            findNavController().navigate(R.id.action_previewRegFragment_to_registerViewPagerFragment)
        }
        return binding.root
    }

}