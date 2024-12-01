package com.example.car_sharing

import android.net.Uri
import androidx.core.content.ContentProviderCompat.requireContext

class ImageUtils {
    private fun handleImageSelection(uri: Uri?, targetKey: String) {
        uri?.let {
            val byteArray = requireContext().contentResolver.openInputStream(uri)?.readBytes()
            byteArray?.let { imageBytes ->
                signUpViewModel.onImageChange(targetKey, imageBytes)
            }
        }
    }
}