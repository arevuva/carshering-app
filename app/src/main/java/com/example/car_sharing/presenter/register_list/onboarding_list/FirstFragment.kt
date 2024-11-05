package com.example.car_sharing.presenter.register_list.onboarding_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentFirstBinding.inflate(inflater, container, false)
        binding.buttonNext1.setOnClickListener{
            activity?.findViewById<ViewPager2>(R.id.vp2)?.currentItem=1
        }
        return binding.root
    }

}