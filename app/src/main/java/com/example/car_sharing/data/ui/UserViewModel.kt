package com.example.car_sharing.data.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.car_sharing.data.db.entities.User
import com.example.car_sharing.data.db.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun login(email: String, password: String): LiveData<Boolean> = liveData {
        val isSuccess = userRepository.login(email, password)
        emit(isSuccess)
    }

    fun register(user: User) = viewModelScope.launch {
        userRepository.register(user)
    }

    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }

    fun logout() {
        userRepository.logout()
    }

}
