package com.example.car_sharing.data.supabase_db

import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

data class CarDto(
    @SerialName("id")
    val id : Int,

    @SerialName("brand_name")
    val brandName: String,

    @SerialName("model_name")
    val modelName: String,

    @SerialName("price_per_day")
    val pricePerDay: Int,

    @SerialName("transmission_type")
    val transmission: String,

    @SerialName("fuel_type")
    val fuelType: String,

    @SerialName("image_url")
    val imageUrl: String,

    @SerialName("location")
    val location: String,  // Добавлено поле для местоположения

    @SerialName("description")
    val description: String // Добавлено поле для описания
)

data class CarFullDto(
    @SerialName("id")
    val id : Int,

    @SerialName("brand_name")
    val brandName: String,

    @SerialName("model_name")
    val modelName: String,

    @SerialName("price_per_day")
    val pricePerDay: Int,

    @SerialName("transmission_type")
    val transmission: String,

    @SerialName("fuel_type")
    val fuelType: String,

    @SerialName("image_url")
    val imageUrl: String,

    @SerialName("location")
    val location: String,  // Добавлено поле для местоположения

    @SerialName("description")
    val description: String // Добавлено поле для описания
)