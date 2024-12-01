package com.example.car_sharing.presenter.common_presenter.onboarding_list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.car_sharing.R
import com.example.car_sharing.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentThirdBinding.inflate(inflater, container, false)
        binding.buttonNext3.setOnClickListener{
            this.findNavController().navigate(R.id.action_viewPagerFragment_to_previewRegFragment)
            onBoardingFinished()
        }
        return binding.root
    }
    fun onBoardingFinished(){
        val sharedPref=requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }
}