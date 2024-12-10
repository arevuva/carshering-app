package com.example.car_sharing.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_sharing.data.supabase_db.Car
import com.example.car_sharing.data.supabase_db.CarDto
import com.example.car_sharing.data.repositories.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarListViewModel @Inject constructor(
    private val carRepository: CarRepository,
    ):ViewModel() {
    private val _carList = MutableStateFlow<List<Car>?>(listOf())
    val carList: Flow<List<Car>?> get()= _carList


    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    init {
        getCars()
    }

    fun getCars() {
        viewModelScope.launch {
            val cars = carRepository.getCars()
            _carList.emit(cars?.map { it -> it.asDomainModel() })
        }
    }

//    fun removeItem(car: Car) {
//        viewModelScope.launch {
//            val newList = mutableListOf<Car>().apply { _carList.value?.let { addAll(it) } }
//            newList.remove(car)
//            _carList.emit(newList.toList())
//            // Call API to remove
//            carRepository.deleteCar(id = car.id)
//            // Then fetch again
//            getCars()
//        }
//    }

    private fun CarDto.asDomainModel(): Car {
        return Car(
            carId = this.id,
            brandName = this.brandName,
            modelName = this.modelName,
            pricePerDay = this.pricePerDay,
            transmission = this.transmission,
            fuelType = this.fuelType,
            imageUrl = this.imageUrl
        )
    }


}