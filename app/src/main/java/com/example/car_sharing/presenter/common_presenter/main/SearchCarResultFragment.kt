package com.example.car_sharing.presenter.common_presenter.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.car_sharing.data.db.entities.Car
import com.example.car_sharing.data.ui.CarAdapter
import com.example.car_sharing.databinding.FragmentSearchCarResultBinding


class SearchCarResultFragment : Fragment() {

    private var _binding: FragmentSearchCarResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchCarResultBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создаем список фиктивных машин
        val Cars = listOf(
            Car(
                brand = "Mercedes-Benz",
                model = "S 500",
                pricePerDay = 2500,
                transmission = "A/T",
                fuelType = "Бензин",
                imageUrl = "https://example.com/image1.jpg"
            ),
            Car(
                brand = "BMW",
                model = "X5",
                pricePerDay = 3000,
                transmission = "A/T",
                fuelType = "Бензин",
                imageUrl = "https://example.com/image2.jpg"
            ),



        )

        // Настройка RecyclerView
        binding.carRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.carRecyclerView.adapter = CarAdapter(Cars)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}