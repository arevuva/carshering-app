package com.example.car_sharing.register_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.car_sharing.R
import com.example.car_sharing.databinding.FragmentFirstBinding
import com.example.car_sharing.databinding.FragmentRegFinishBinding
import com.example.car_sharing.databinding.FragmentRegThirdBinding


class RegFinishFragment : Fragment() {

    private var _binding: FragmentRegFinishBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentRegFinishBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "Регистрация завершена", Toast.LENGTH_LONG).show()
    }

}