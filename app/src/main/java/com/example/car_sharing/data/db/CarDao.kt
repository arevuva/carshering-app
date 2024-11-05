package com.example.car_sharing.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.car_sharing.data.db.entities.Car

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(car: Car)

    @Delete
    suspend fun delete(car: Car)

    @Query("SELECT * FROM cars")
    fun getAllCars(): LiveData<List<Car>>
}
