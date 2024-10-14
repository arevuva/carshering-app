package com.example.car_sharing.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.databinding.FragmentFirstBinding
import com.example.car_sharing.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentSecondBinding.inflate(inflater, container, false)
        binding.buttonNext2.setOnClickListener{
            activity?.findViewById<ViewPager2>(R.id.vp2)?.currentItem=2
        }
        return binding.root
    }

}