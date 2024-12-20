package com.example.car_sharing.data.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.car_sharing.R
import com.example.car_sharing.data.supabase_db.Car
import com.example.car_sharing.databinding.ItemCarBinding

class CarAdapter(
    private val cars: List<Car>,
    private val onCarClicked: (String) -> Unit
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    inner class CarViewHolder(private val binding: ItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Car) {
            // Устанавливаем текстовые данные
            binding.textViewBrand.text = item.brandName
            binding.textViewCarModel.text = item.modelName
            binding.textViewCarPrice.text = "${item.pricePerDay}₽ в день"
            binding.textViewCarTransmission.text = item.transmission
            binding.textViewCarFuelType.text = item.fuelType

            // Загружаем изображение через Glide
            Glide.with(binding.imageViewCar.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.car_phone) // Заглушка на время загрузки
                .error(R.drawable.car_phone) // Заглушка при ошибке
                .into(binding.imageViewCar)
            binding.buttonDetails

            binding.buttonDetails.setOnClickListener {
                onCarClicked(item.carId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(cars[position])
    }

    override fun getItemCount() = cars.size
}
