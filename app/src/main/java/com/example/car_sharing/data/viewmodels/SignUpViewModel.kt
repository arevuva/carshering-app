package com.example.car_sharing.data.viewmodels

import android.util.Log
import android.widget.Toast
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
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _firstName = MutableStateFlow("")
    private val _secondName = MutableStateFlow("")
    private val _thirdName = MutableStateFlow("")
    private val _gender = MutableStateFlow("")
    private val _phoneNumber = MutableStateFlow("")
    private val _dateBirth = MutableStateFlow("")
    private val _dateDriverLicense = MutableStateFlow("")
    private val _numberDriverLicense = MutableStateFlow("")

    private val _userPhotoName = MutableStateFlow("")
    private val _driverLicensePhotoName = MutableStateFlow("")
    private val _passportPhotoName = MutableStateFlow("")

    private val _userPhoto = MutableLiveData<ByteArray>()
    val userPhoto: LiveData<ByteArray> get() = _userPhoto
    private val _driverLicensePhoto =  MutableLiveData<ByteArray>()
    val driverLicensePhoto: LiveData<ByteArray> get() = _driverLicensePhoto
    private val _passportPhoto =  MutableLiveData<ByteArray>()
    val passportPhoto: LiveData<ByteArray> get() = _passportPhoto



    val email: Flow<String> = _email
    val password = _password

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }


    fun onFirstNameChange(firstName: String) {
        _firstName.value = firstName
    }

    fun onSecondNameChange(secondName: String) {
        _secondName.value = secondName
    }

    fun onThirdNameChange(thirdName: String) {
        _thirdName.value = thirdName
    }

    fun onGenderChange(gender: String) {
        _gender.value = gender
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun onDateBirthChange(dateBirth: String) {
        _dateBirth.value = dateBirth
    }

    fun onDateDriverLicenseChange(dateDriverLicense: String) {
        _dateDriverLicense.value = dateDriverLicense
    }

    fun onNumberDriverLicenseChange(numberDriverLicense: String) {
        _numberDriverLicense.value = numberDriverLicense
    }

    fun onUserPhotoChange(imageBytes: ByteArray, fileName: String) {
        _userPhoto.value = imageBytes
        _userPhotoName.value = fileName
    }

    fun onDriverLicensePhotoChange(driverLicensePhoto: ByteArray, fileName: String) {
        _driverLicensePhoto.value = driverLicensePhoto
        _driverLicensePhotoName.value = fileName
    }

    fun onPassportPhotoChange(passportPhoto: ByteArray, fileName: String) {
        _passportPhoto.value = passportPhoto
        _passportPhotoName.value = fileName
    }


    fun onSignUp() {
        viewModelScope.launch {
            userRepository.signUp(
                email = _email.value,
                password = _password.value,
                firstName = _firstName.value,
                secondName = _secondName.value,
                thirdName = _thirdName.value,
                gender = _gender.value,
//                phoneNumber = _phoneNumber.value,
                dateBirth = _dateBirth.value,
                dateDriverLicense = _dateDriverLicense.value,
                numberDriverLicense = _numberDriverLicense.value,
                userPhotoName = _userPhotoName.value,
                userPhotoFile = userPhoto.value?:ByteArray(0),
                driverLicensePhotoName = _driverLicensePhotoName.value,
                driverLicensePhotoFile = _driverLicensePhoto.value?:ByteArray(0),
                passportPhotoName = _passportPhotoName.value,
                passportPhotoFile = _passportPhoto.value?:ByteArray(0)
            )
        }
    }
}

