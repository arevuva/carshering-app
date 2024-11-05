package com.example.car_sharing.data.db.repositories

import androidx.lifecycle.LiveData
import com.example.car_sharing.data.db.CarDao
import com.example.car_sharing.data.db.entities.Car
import io.github.jan.supabase.SupabaseClient

class CarRepository(private val carDao: CarDao) {
    val allCars: LiveData<List<Car>> = carDao.getAllCars()

    suspend fun insert(car: Car) = carDao.insert(car)
    suspend fun delete(car: Car) = carDao.delete(car)
}

