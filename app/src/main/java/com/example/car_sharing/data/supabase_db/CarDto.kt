package com.example.car_sharing.data.supabase_db

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarDto(
    @SerialName("id")
    val id : String,

    @SerialName("brandName")
    val brandName: String,

    @SerialName("modelName")
    val modelName: String,

    @SerialName("pricePerDay")
    val pricePerDay: Int,

    @SerialName("transmission")
    val transmission: String,

    @SerialName("fuelType")
    val fuelType: String,

    @SerialName("imageUrl")
    val imageUrl: String,

)

@Serializable
data class CarFullDto(
    @SerialName("id")
    val id : String,

    @SerialName("brandName")
    val brandName: String,

    @SerialName("modelName")
    val modelName: String,

    @SerialName("pricePerDay")
    val pricePerDay: Int,

    @SerialName("transmission")
    val transmission: String,

    @SerialName("fuelType")
    val fuelType: String,

    @SerialName("imageUrl")
    val imageUrl: String,

    @SerialName("location")
    val location: String,

    @SerialName("description")
    val description: String,

    @SerialName("available")
    val available: Boolean
)