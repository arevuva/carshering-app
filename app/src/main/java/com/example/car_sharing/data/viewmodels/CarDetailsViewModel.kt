package com.example.car_sharing.data.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_sharing.data.repositories.CarRepository
import com.example.car_sharing.data.repositories.UserRepository
import com.example.car_sharing.data.supabase_db.Car
import com.example.car_sharing.data.supabase_db.CarFullDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailsViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val userRepository: UserRepository,
): ViewModel() {
    private val _carDetails = MutableStateFlow<Car?>(null)
    val getCar: StateFlow<Car?> get() = _carDetails

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean> get() = _isFavourite

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun checkIfCarIsFavourite(carId: String) {
        viewModelScope.launch {
            val result = carRepository.isCarInFavourites(userRepository.getCurrentUserId(), carId)
            _isFavourite.value = result
        }
    }

    fun getCarById(carId: String) {
        viewModelScope.launch {
            try {
                val carDto = carRepository.getCar(carId)
                val car = carDto?.asDomainModel()
                _carDetails.emit(car)
            } catch (e: Exception) {
                _error.emit("Не удалось загрузить данные автомобиля")
            }
        }
    }

    fun addCarToFavourites(carId: String) {
        viewModelScope.launch {
            try {
                val userid = userRepository.getCurrentUserId()
                carRepository.addCarToFavourites(userid, carId)
                _isFavourite.value=true
            } catch (e: Exception) {
                Log.e("Ошибка при добавлении машины в избранные", e.toString())
            }
        }
    }

    fun removeCarFromFavourites(carId: String) {
        viewModelScope.launch {
//        val isFav = carRepository.isCarInFavourites(userId, carId)
            try {
                val userId = userRepository.getCurrentUserId()
                carRepository.removeCarFromFavourites(userId, carId)
                _isFavourite.value=false
            } catch (e: Exception) {
                Log.e("Ошибка при убирании из списка избранных", e.toString())
            }
        }
    }

    private fun CarFullDto.asDomainModel(): Car {
        var car = Car(
            carId = this.id,
            brandName = this.brandName,
            modelName = this.modelName,
            pricePerDay = this.pricePerDay,
            transmission = this.transmission,
            fuelType = this.fuelType,
            imageUrl = this.imageUrl,
            description = this.description,
            location = this.location,
            available = this.available,
        )
        return car
    }
}