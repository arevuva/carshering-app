package com.example.car_sharing.presenter.register_list.register

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.databinding.FragmentRegThirdBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegThirdFragment : Fragment() {
    private var _binding: FragmentRegThirdBinding? = null
    private val binding get() = _binding!!
    var isValid = true
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentRegThirdBinding.inflate(inflater, container, false)
        return binding.root
    }
    private var selectedImageView: ImageView? = null

    // Лаунчер для получения результата выбора изображения
    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            selectedImageView?.setImageURI(imageUri)  // Отображаем выбранное изображение в ImageView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Устанавливаем обработчик для каждого ImageView
        binding.avatarImageView.setOnClickListener {
            selectImageFromGallery(binding.avatarImageView)
        }

        binding.uploadDriverLicenseView.setOnClickListener {
            selectImageFromGallery(binding.uploadDriverLicenseView)
        }

        binding.uploadPassportView.setOnClickListener {
            selectImageFromGallery(binding.uploadPassportView)
        }
        binding.nextButton.setOnClickListener{
            validateInput()
            if(isValid){
                activity?.findViewById<ViewPager2>(R.id.vpreg2)?.currentItem = 3
            }
        }
        binding.etDriverDate.setOnClickListener {
            showDatePickerDialog()
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

    // Функция для выбора изображения из галереи
    private fun selectImageFromGallery(imageView: ImageView) {
        selectedImageView = imageView
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectImageLauncher.launch(intent)
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
            binding.driverLicenseEditText.error=null
        }

    }

}