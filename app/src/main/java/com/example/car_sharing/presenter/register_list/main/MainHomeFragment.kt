package com.example.car_sharing.presenter.register_list.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.car_sharing.R
import com.example.car_sharing.data.db.entities.Car
import com.example.car_sharing.data.ui.CarAdapter
import com.example.car_sharing.databinding.FragmentMainHomeBinding


class MainHomeFragment : Fragment() {

    private var _binding: FragmentMainHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainHomeBinding.inflate(inflater, container, false)
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
            Car(
                brand = "Toyota",
                model = "Camry",
                pricePerDay = 1500,
                transmission = "A/T",
                fuelType = "Бензин",
                imageUrl = "https://avatars.mds.yandex.net/i?id=663501823c62adbf46f82dc1bf027f9b_l-5268458-images-thumbs&n=13"
            ),
            Car(
                brand = "Toyota",
                model = "Camry",
                pricePerDay = 1500,
                transmission = "A/T",
                fuelType = "Бензин",
                imageUrl = "https://avatars.mds.yandex.net/i?id=663501823c62adbf46f82dc1bf027f9b_l-5268458-images-thumbs&n=13"
            ),
            Car(
                brand = "Toyota",
                model = "Camry",
                pricePerDay = 1500,
                transmission = "A/T",
                fuelType = "Бензин",
                imageUrl = "https://avatars.mds.yandex.net/i?id=663501823c62adbf46f82dc1bf027f9b_l-5268458-images-thumbs&n=13"
            )



        )
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
        binding.carRecyclerView.adapter = CarAdapter(Cars)


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}