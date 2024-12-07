package com.example.car_sharing.data.repositories

import com.example.car_sharing.data.supabase_db.CarDto
import com.example.car_sharing.data.supabase_db.CarFullDto
import com.example.car_sharing.data.supabase_db.SPConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
            val result = postgrest.from("cars")
                .select().decodeList<CarDto>()
            result
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
            postgrest.from("cars").select(Columns.list("id", "name", "brand")) {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<CarFullDto>()
        }
    }
    override fun buildImageUrl(imageFileName: String) =
        "${SPConfig.supabaseUrl}/storage/v1/object/public/${imageFileName}"

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