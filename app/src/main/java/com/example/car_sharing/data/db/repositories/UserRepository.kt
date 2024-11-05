package com.example.car_sharing.data.db.repositories

import android.content.Context
import android.content.SharedPreferences
import com.example.car_sharing.data.db.entities.User
import com.example.car_sharing.data.db.UserDao

class UserRepository(private val userDao: UserDao, private val prefs: SharedPreferences) {

    suspend fun login(email: String, password: String): Boolean {
        val user = userDao.login(email, password)
        return if (user != null) {
            prefs.edit().putBoolean("isLoggedIn", true).apply()
            true
        } else {
            false
        }
    }

    suspend fun register(user: User) {
        userDao.register(user)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun logout() {
        prefs.edit().remove("isLoggedIn").apply()
    }
}

