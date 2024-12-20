import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")

//    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}



android {

    namespace = "com.example.car_sharing"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.car_sharing"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }


}

dependencies {
    implementation("com.tbuonomo:dotsindicator:5.0")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Проверьте последнюю версию
    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.1"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.ktor:ktor-client-android:3.0.0")
    implementation(libs.androidx.core.ktx)
//    val room_version = "2.6.1"
//    implementation("androidx.room:room-runtime:$room_version")
//    annotationProcessor("androidx.room:room-compiler:$room_version")
//    implementation ("androidx.room:room-runtime:2.6.1") // Библиотека "Room"
//    kapt("androidx.room:room-compiler:$room_version") // Кодогенератор

    val supabase_version = "3.0.2"
    val ktor_version = "3.0.0"
    implementation ("io.github.jan-tennert.supabase:postgrest-kt:$supabase_version")
    implementation ("io.github.jan-tennert.supabase:storage-kt:$supabase_version")
    implementation ("io.github.jan-tennert.supabase:auth-kt:$supabase_version")
    implementation ("io.ktor:ktor-client-android:$ktor_version")
    implementation ("io.ktor:ktor-client-core:$ktor_version")
    implementation ("io.ktor:ktor-utils:$ktor_version")

    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation(libs.androidx.appcompat)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("org.kodein.di:kodein-di-generic-jvm:6.3.3")
    implementation("org.kodein.di:kodein-di-framework-android-x:6.3.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation (libs.androidx.navigation.fragment.ktx.v284)
    implementation (libs.androidx.navigation.ui.ktx.v284)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}