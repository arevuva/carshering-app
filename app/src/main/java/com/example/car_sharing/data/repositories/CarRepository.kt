package com.example.car_sharing.data.repositories

import android.util.Log
import com.example.car_sharing.data.supabase_db.CarDto
import com.example.car_sharing.data.supabase_db.CarFullDto
import com.example.car_sharing.data.supabase_db.SPConfig
import com.example.car_sharing.data.supabase_db.UserCarDto
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Count
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kodein.di.bindings.WithContext
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
abstract class CarRepositoryModule {

    @Binds
    abstract fun bindCarRepository(
        carRepositoryImpl: CarRepositoryImpl
    ): CarRepository
}


interface CarRepository {
//    suspend fun createCar(car: Car): Boolean
    suspend fun getCars(): List<CarDto>?
    suspend fun getFavouriteCars(): List<CarDto>?
    suspend fun getCar(id: String): CarFullDto?
    fun buildImageUrl(imageFileName: String):String
    suspend fun addCarToFavourites(userId: String, carId: String)
    suspend fun removeCarFromFavourites(userId: String, carId: String)
    suspend fun isCarInFavourites(userId: String, carId: String): Boolean
    suspend fun submitRent(carId: String,userId: String, formattedStartDate: String,  formattedEndDate: String)
//    suspend fun deleteCar(id: String)
//    suspend fun updateCar(
//        id: String, name: String, price: Double, imageName: String, imageFile: ByteArray
//    )
}

class CarRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
) : CarRepository {

    override suspend fun getCars(): List<CarDto>? {
        return withContext(Dispatchers.IO) {
            postgrest.from("cars").select(Columns.list("id", "brandName", "modelName", "pricePerDay","transmission", "fuelType", "imageUrl")) {
                filter {
                    eq("available", true)
                }
            }.decodeList<CarDto>()
        }
    }


    override suspend fun getFavouriteCars(): List<CarDto>? {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("cars")
                .select().decodeList<CarDto>()
            result
        }
    }


    override suspend fun getCar(id: String): CarFullDto {
        return withContext(Dispatchers.IO) {
            postgrest.from("cars").select {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<CarFullDto>()
        }
    }
    override suspend fun isCarInFavourites(userId: String, carId: String): Boolean {
        // Получаем данные из таблицы car_favourites, фильтруя по userId и carId
        val responseCount = withContext(Dispatchers.IO) {
            postgrest.from("car_favourites").select() {
//                count(Count.EXACT)
                filter {
                    and{
                        eq("id_user", userId)  // Фильтрация по userId
                        eq("id_car", carId)
                    }
                }
            }
        }
        val count = responseCount.countOrNull()
        return count != null
    }
    override suspend fun addCarToFavourites(userId: String, carId: String) {
        try {
            val response = postgrest
                .from("car_favourites").insert(
                    mapOf("id_car" to carId, "id_user" to userId))

        }
        catch (e: java.lang.Exception){
            Log.e("Ошибка при добавлении в любимые", e.toString())
        }

    }

    // Функция для удаления машины из избранного
    override suspend fun removeCarFromFavourites(userId: String, carId: String) {
        try {
            val response = withContext(Dispatchers.IO) {
                postgrest
                    .from("car_favourites")
                    .delete() {
                        filter {
                            and {
                                eq("id_user", userId)
                                eq("id_car", carId)
                            }
                        }
                    }
            }
        }
        catch (e: java.lang.Exception){
            Log.e("Ошибка при удалении ", e.toString())
        }
    }
    override suspend fun submitRent(carId: String, userId: String, formattedStartDate: String,  formattedEndDate: String){
        try {
            val response = postgrest
                .from("rents").insert(
                    mapOf("id_car" to carId, "id_user" to userId, "start_rent" to formattedStartDate, "end_rent" to formattedEndDate))

        }
        catch (e: java.lang.Exception){
            Log.e("Ошибка при добавлении аренды", e.toString())
        }
    }


    override fun buildImageUrl(imageFileName: String) =
        "https://swtfrzmikshmbyahtbvg.supabase.co/storage/v1/object/public/cars/${imageFileName}"

//    override suspend fun updateProduct(
//        id: String,
//        name: String,
//        price: Double,
//        imageName: String,
//        imageFile: ByteArray
//    ) {
//        withContext(Dispatchers.IO) {
//            if (imageFile.isNotEmpty()) {
//                val imageUrl =
//                    storage.from("Product%20Image").upload(
//                        path = "$imageName.png",
//                        data = imageFile,
//                        upsert = true
//                    )
//                postgrest.from("products").update({
//                    set("name", name)
//                    set("price", price)
//                    set("image", buildImageUrl(imageFileName = imageUrl))
//                }) {
//                    filter {
//                        eq("id", id)
//                    }
//                }
//            } else {
//                postgrest.from("products").update({
//                    set("name", name)
//                    set("price", price)
//                }) {
//                    filter {
//                        eq("id", id)
//                    }
//                }
//            }
//        }
//    }
//    override suspend fun deleteProduct(id: String) {
//        return withContext(Dispatchers.IO) {
//            postgrest.from("products").delete {
//                filter {
//                    eq("id", id)
//                }
//            }
//        }
//    }


//    override suspend fun createCar(car: Car): Boolean {
//        return try {
//            withContext(Dispatchers.IO) {
//                val productDto = CarDto(
//                    name = car.name,
//                    price = car.price,
//                )
//                postgrest.from("products").insert(productDto)
//                true
//            }
//            true
//        } catch (e: java.lang.Exception) {
//            throw e
//        }
//    }

    // Because I named the bucket as "Product Image" so when it turns to an url, it is "%20"
    // For better approach, you should create your bucket name without space symbol

}