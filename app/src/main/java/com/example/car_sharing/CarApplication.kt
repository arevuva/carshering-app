package com.example.car_sharing

import android.app.Application
import com.example.car_sharing.data.db.AppDatabase
import com.example.car_sharing.data.db.repositories.CarRepository
import com.example.car_sharing.data.db.repositories.UserRepository
import com.example.car_sharing.data.ui.CarViewModel
import com.example.car_sharing.data.ui.UserViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CarApplication : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        bind() from singleton { AppDatabase.getInstance(this@CarApplication) }
        bind() from singleton { CarRepository(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { CarViewModel(instance()) }
        bind() from provider { UserViewModel(instance()) }
    }
}
