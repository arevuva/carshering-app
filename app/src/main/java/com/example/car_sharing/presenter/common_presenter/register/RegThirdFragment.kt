package com.example.car_sharing.presenter.common_presenter.register

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.data.viewmodels.SignUpViewModel
import com.example.car_sharing.databinding.FragmentRegThirdBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegThirdFragment : Fragment() {
    private var _binding: FragmentRegThirdBinding? = null
    private val binding get() = _binding!!
    var isValid = true
    private val calendar = Calendar.getInstance()
    private val signUpViewModel: SignUpViewModel by activityViewModels()
    private val imageKeyMap = mutableMapOf<ImageView, String>()
    private var selectedImageView: ImageView? = null

    // Лаунчер для получения результата выбора изображения
    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let { uri ->
                    val fileName = getFileNameFromUri(uri) // Получаем название файла
                    selectedImageView?.setImageURI(uri) // Отображаем изображение в ImageView

                    // Читаем байты изображения и сжимаем
                    val compressedBytes = compressImage(uri)
                    compressedBytes?.let { bytes ->
                        onImageSelectedCallback?.invoke(bytes, fileName) // Передаем сжатые байты и название файла

                    }
                }
            }
        }
    // Функция для сжатия изображения
    private fun compressImage(uri: Uri, maxWidth: Int = 600, maxHeight: Int = 600, quality: Int = 80): ByteArray? {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            // Вычисляем новые размеры изображения
            val aspectRatio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
            val width = if (originalBitmap.width > originalBitmap.height) maxWidth else (maxHeight * aspectRatio).toInt()
            val height = if (originalBitmap.height > originalBitmap.width) maxHeight else (maxWidth / aspectRatio).toInt()

            // Создаем измененный Bitmap
            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true)

            // Сжимаем изображение в JPEG с заданным качеством
            val outputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

            return outputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    // Колбэк для обработки выбранного изображения
    private var onImageSelectedCallback: ((ByteArray, String) -> Unit)? = null

    private fun getFileNameFromUri(uri: Uri): String {
        var fileName = "unknown_file"
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            if (nameIndex >= 0 && cursor.moveToFirst()) {
                fileName = cursor.getString(nameIndex)
            }
        }
        return fileName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentRegThirdBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etNumDriverLicense.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signUpViewModel.onNumberDriverLicenseChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.etDriverDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.etDriverDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signUpViewModel.onDateDriverLicenseChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        // Устанавливаем обработчик для каждого ImageView
        binding.avatarImageView.setOnClickListener {
            selectImageFromGallery(binding.avatarImageView) { imageBytes, fileName ->
                signUpViewModel.onUserPhotoChange(imageBytes, fileName)
            }
        }

        binding.uploadDriverLicenseView.setOnClickListener {
            selectImageFromGallery(binding.uploadDriverLicenseView) { imageBytes, fileName ->
                signUpViewModel.onDriverLicensePhotoChange(imageBytes, fileName)
            }
        }

        binding.uploadPassportView.setOnClickListener {
            selectImageFromGallery(binding.uploadPassportView) { imageBytes, fileName ->
                signUpViewModel.onPassportPhotoChange(imageBytes, fileName)
            }
        }
        binding.nextButton.setOnClickListener{
            validateInput()
            if(isValid){
                activity?.findViewById<ViewPager2>(R.id.vpreg2)?.currentItem = 3
            }
            signUpViewModel.onSignUp()

        }

    }


    // Функция для выбора изображения из галереи
    private fun selectImageFromGallery(
        imageView: ImageView,
        onImageSelected: (ByteArray, String) -> Unit
    ) {
        selectedImageView = imageView
        onImageSelectedCallback = onImageSelected // Сохраняем колбэк
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectImageLauncher.launch(intent)
    }

    private fun showDatePickerDialog() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        // Создание и отображение DatePickerDialog
        DatePickerDialog(
            requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy" // Формат даты
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.etDriverDate.setText(sdf.format(calendar.time))
    }
    private fun validateInput(){
        val serial = binding.etDriverDate.text.toString()
        val driverDate = binding.etDriverDate.text.toString()
        isValid=true
        if (driverDate.isEmpty()) {
            binding.etDriverDateLayout.error = "Выберите дату рождения"
            isValid=false
        } else {
            binding.etDriverDateLayout.error = null
        }
        if (serial.isEmpty() || serial.length < 10) {
            binding.textInputLayoutDriverLicense.error = "Номер водительского удостоверения должен быть в длину 10 символов"
            isValid = false
        }
        else{
            binding.etNumDriverLicense.error=null
        }

    }

}