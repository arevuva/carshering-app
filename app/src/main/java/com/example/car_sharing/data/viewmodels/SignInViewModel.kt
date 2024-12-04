package com.example.car_sharing.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_sharing.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    private val _signInResult = MutableLiveData<Boolean>()
    val signInResult: LiveData<Boolean> get() = _signInResult

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignIn(){
        viewModelScope.launch {
            val result = userRepository.signIn(
                email = _email.value,
                password = _password.value
            )
            _signInResult.postValue(result)
        }
    }

    fun onGoogleSignIn() {
        viewModelScope.launch {
            userRepository.signInWithGoogle()
        }
    }

}
