package com.example.car_sharing.data.repositories

import android.util.Log
import com.example.car_sharing.data.supabase_db.SPConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.Phone
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.FileUploadResponse
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}

interface UserRepository {
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun uploadImage(filePath: String, imageBytes: ByteArray): String?
    fun buildImageUrl(imageFileName: String): String
    suspend fun uploadPhoto(photoName:String, file:ByteArray, email: String): FileUploadResponse
    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        secondName: String,
        thirdName: String,
        gender: String,
//        phoneNumber: String,
        dateBirth: String,
        dateDriverLicense: String,
        numberDriverLicense: String,
        userPhotoName: String,
        userPhotoFile: ByteArray,
        driverLicensePhotoName: String,
        driverLicensePhotoFile: ByteArray,
        passportPhotoName: String,
        passportPhotoFile: ByteArray,
    ): Boolean
    suspend fun signInWithGoogle(): Boolean

}

class UserRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest,
    private val storage: Storage
) : UserRepository {

    fun parseDate(dateString: String): String {
        // Укажите исходный формат даты (если он отличается от желаемого)
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        // Преобразование
        val date: Date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password

            }
            // Получаем userId после успешного входа
            val user = auth.currentUserOrNull()
            if (user == null) {
                println("Failed to retrieve current user after login.")
                return false
            }
            val userId = user.id
            val metadata = user.userMetadata

            // Извлекаем дополнительные данные из таблицы "users"
//            withContext(Dispatchers.IO) {
//                val userInfo = postgrest.from("users")
//                    .select {
//                        filter {
//                            eq("id", userId)
//                        }
//                    }
//                println("User Info: $userInfo")
//            }

            true
        } catch (e: Exception) {
            Log.e("UserRepository", e.toString())
            false
        }

    }

    override suspend fun uploadImage(filePath: String, imageBytes: ByteArray): String? {
        return try {
            // Загружаем изображение в хранилище
            val uploadResult = storage.from("user-photo").upload(filePath, imageBytes)
            print(uploadResult.path)
            uploadResult.path
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun signUp(
        email: String, password: String, firstName: String, secondName: String, thirdName: String, gender: String,
         dateBirth: String, dateDriverLicense: String, numberDriverLicense: String,
//        phoneNumber: String,
        userPhotoName: String, userPhotoFile: ByteArray,
        driverLicensePhotoName: String, driverLicensePhotoFile: ByteArray,
        passportPhotoName: String,
        passportPhotoFile: ByteArray,
    ): Boolean {
        return try {
            withContext(Dispatchers.IO) {



                auth.signUpWith(Email) {
                    this.email = email
                    this.password = password

                    this.data = buildJsonObject {
                        put("first_name", firstName)
                        put("second_name", secondName)
                        put("third_name", thirdName)
                        put("gender", gender)
//                        put("phone_number", phoneNumber)
                        put("date_birth", parseDate(dateBirth))
                        put("date_driver_license", parseDate(dateDriverLicense))
                        put("number_driver_license", numberDriverLicense)
                        put("user_photo", buildImageUrl(email+userPhotoName) )
                        put("driver_license_photo", buildImageUrl(email+driverLicensePhotoName))
                        put("passport_photo", buildImageUrl(email+passportPhotoName))
                    }
                }
                val buckets = storage.retrieveBuckets()
                Log.d("BBUCKETS", buckets.size.toString())

                uploadPhoto(email+userPhotoName,userPhotoFile, email)
                uploadPhoto(email+driverLicensePhotoName,driverLicensePhotoFile, email)
                uploadPhoto(email+passportPhotoName,passportPhotoFile, email)
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun uploadPhoto(photoName:String, photoile:ByteArray, email: String): FileUploadResponse {

        return storage.from("users-photo").upload(
            path = "$photoName",
            data = photoile,
            options = { upsert = false }
        )

    }
    override fun buildImageUrl(imageFileName: String) =
        "https://swtfrzmikshmbyahtbvg.supabase.co/storage/v1/object/public/users-photo/${imageFileName}"

    override suspend fun signInWithGoogle(): Boolean {
        return try {
            auth.signInWith(Google)
            true
        } catch (e: Exception) {
            false
        }
    }
}

//            // Доступ к данным
//            val firstName = metadata?.get("first_name") as? String ?: "unknown"
//            val secondName = metadata?.get("second_name") as? String ?: "unknown"
//            val thirdName = metadata?.get("third_name") as? String ?: "unknown"
//            val gender = metadata?.get("gender") as? String?: "unknown"
//            val phoneNumber = metadata?.get("phone_number") as? String?: "unknown"
//            val dateBirth = metadata?.get("date_birth") as? String?: "unknown"
//            val dateDriverLicense = metadata?.get("date_driver_license") as? String?: "unknown"
//            val numberDriverLicense = metadata?.get("number_driver_license") as? String?: "unknown"
//            val userPhoto = metadata?.get("user_photo") as? String?: "unknown"
//            val driverLicensePhoto = metadata?.get("driver_license_photo") as? String?: "unknown"
//            val passportPhoto = metadata?.get("passport_photo") as? String?: "unknown"
