package com.example.car_sharing.data.supabase_db

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class Car(
    val carId: String,
    val brandName: String,
    val modelName: String,
    val pricePerDay: Int,
    val transmission: String,
    val fuelType: String,
    val imageUrl: String,
    val location: String = "",  // Добавлено поле для местоположения
    val description: String = "", // Добавлено поле для описания
    val available: Boolean = true,
    val favourite: Boolean = true
) : Parcelable

