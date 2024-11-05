package com.example.car_sharing.data.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_sharing.data.db.entities.Car
import com.example.car_sharing.data.db.repositories.CarRepository
import kotlinx.coroutines.launch

class CarViewModel(private val repository: CarRepository) : ViewModel() {
    val allCars: LiveData<List<Car>> = repository.allCars

    fun insert(car: Car) = viewModelScope.launch {
        repository.insert(car)
    }

    fun delete(car: Car) = viewModelScope.launch {
        repository.delete(car)
    }
}