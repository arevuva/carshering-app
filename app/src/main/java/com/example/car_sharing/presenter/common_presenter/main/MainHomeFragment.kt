package com.example.car_sharing.presenter.common_presenter.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.car_sharing.R
import com.example.car_sharing.data.supabase_db.Car
import com.example.car_sharing.data.ui.CarAdapter
import com.example.car_sharing.data.viewmodels.CarListViewModel
import com.example.car_sharing.databinding.FragmentMainHomeBinding
import kotlinx.coroutines.launch


class MainHomeFragment : Fragment() {

    private var _binding: FragmentMainHomeBinding? = null
    private val binding get() = _binding!!
    private val carListViewModel: CarListViewModel by activityViewModels() // Общая ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                Toast.makeText(requireContext(), "vwevwev",Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_mainHomeFragment_to_searchCarResultFragment)
                true
            } else {
                false
            }
        }

        // Настройка RecyclerView
        binding.carRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            carListViewModel.getCars()
            carListViewModel.carList.collect { cars ->
                binding.carRecyclerView.adapter = CarAdapter(cars ?: emptyList()) { carId ->
                    val action = MainHomeFragmentDirections.actionMainHomeFragmentToCarDetailFragment(carId)
                    findNavController().navigate(action)
                }
                }
            }
        }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}