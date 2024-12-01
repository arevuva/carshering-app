package com.example.car_sharing.presenter.common_presenter.register

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
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
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.data.viewmodels.SignUpViewModel
import com.example.car_sharing.databinding.FragmentRegThirdBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegThirdFragment : Fragment() {
    private var _binding: FragmentRegThirdBinding? = null
    private val binding get() = _binding!!
    var isValid = true
    private val calendar = Calendar.getInstance()
    private val signUpViewModel: SignUpViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    private val imageKeyMap = mutableMapOf<ImageView, String>()
    private var selectedImageView: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentRegThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Лаунчер для получения результата выбора изображения
    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            selectedImageView?.setImageURI(imageUri)  // Отображаем выбранное изображение в ImageView
        }
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

        // Устанавливаем обработчик для каждого ImageView
        binding.avatarImageView.setOnClickListener {
            selectImageFromGallery(binding.avatarImageView) { imageBytes ->
                signUpViewModel.onUserPhotoChange(imageBytes)
            }
        }
        binding.uploadDriverLicenseView.setOnClickListener {
            selectImageFromGallery(binding.uploadDriverLicenseView) { imageBytes ->
                signUpViewModel.onDriverLicensePhotoChange(imageBytes)
            }
        }
        binding.uploadPassportView.setOnClickListener {
            selectImageFromGallery(binding.uploadPassportView) { imageBytes ->
                signUpViewModel.onPassportPhotoChange(imageBytes)
            }
        }
        binding.nextButton.setOnClickListener{
            validateInput()
            if(isValid){
                activity?.findViewById<ViewPager2>(R.id.vpreg2)?.currentItem = 3
            }
        }


    }


    // Функция для выбора изображения из галереи
    private fun selectImageFromGallery(imageView: ImageView, onImageSelected: (ByteArray) -> Unit) {
        selectedImageView = imageView
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectImageLauncher.launch(intent)

        // Лаунчер для обработки результата
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                val byteArray = imageUri?.let { uri ->
                    requireContext().contentResolver.openInputStream(uri)?.readBytes()
                }
                byteArray?.let {
                    selectedImageView?.setImageURI(imageUri) // Отображение изображения
                    onImageSelected(it) // Обновление ViewModel
                }
            }
        }
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