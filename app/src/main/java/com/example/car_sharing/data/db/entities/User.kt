package com.example.car_sharing.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val email: String,
    val password: String,
    val createdAt: String,
    val firstName: String,
    val secondName: String,
    val thirdName: String?,
    val gender: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val dateDriverLicense: String,
    val numberDriverLicense: String,
    val userPhoto: String?, // URL для фото профиля
    val driverLicensePhoto: String?, // URL для фото водительского удостоверения
    val passportPhoto: String? // URL для фото паспорта
)