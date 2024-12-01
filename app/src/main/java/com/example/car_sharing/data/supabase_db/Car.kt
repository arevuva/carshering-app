package com.example.car_sharing.data.supabase_db

import kotlinx.serialization.SerialName

data class Car(
    val carId: Int,
    val brandName: String,
    val modelName: String,
    val pricePerDay: Int,
    val transmission: String,
    val fuelType: String,
    val imageUrl: String,
    val location: String,  // Добавлено поле для местоположения
    val description: String // Добавлено поле для описания

)

