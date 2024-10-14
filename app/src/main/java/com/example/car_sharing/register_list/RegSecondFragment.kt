package com.example.car_sharing.register_list

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.databinding.FragmentRegSecondBinding
import com.example.car_sharing.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegSecondFragment : Fragment() {
    private var _binding: FragmentRegSecondBinding? = null
    private val binding get() = _binding!!
    private var isValid=true
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Настройка поля ввода даты рождения
        binding.etBirthDate.inputType = InputType.TYPE_NULL // Отключаем стандартную клавиатуру

        binding.etBirthDate.setOnClickListener {
            showDatePickerDialog()
        }


        // Обработчик нажатия на кнопку "Далее"
        binding.btnNextReg2.setOnClickListener {
            val lastName = binding.etLastName.text.toString().trim()
            val firstName = binding.etFirstName.text.toString().trim()
            val middleName = binding.etMiddleName.text.toString().trim()
            val birthDate = binding.etBirthDate.text.toString().trim()
            val gender = if (binding.rbMale.isChecked) "Мужской" else "Женский"

            // Валидация данных
            validateInput(lastName, firstName, middleName, birthDate)
            if (isValid) {
                activity?.findViewById<ViewPager2>(R.id.vpreg2)?.currentItem = 2
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
        binding.etBirthDate.setText(sdf.format(calendar.time))
    }

    private fun validateInput(
        lastName: String,
        firstName: String,
        middleName: String,
        birthDate: String
    ) {
        isValid=true
        if (lastName.isEmpty()) {
            binding.etLastNameLayout.error = "Введите фамилию"
            isValid=false
        } else {
            binding.etLastNameLayout.error = null
        }

        if (firstName.isEmpty()) {
            binding.etFirstNameLayout.error = "Введите имя"
            isValid=false
        } else {
            binding.etFirstNameLayout.error = null
        }

        if (middleName.isEmpty()) {
            binding.etMiddleNameLayout.error = "Введите отчество"
            isValid=false
        } else {
            binding.etMiddleNameLayout.error = null
        }

        if (birthDate.isEmpty()) {
            binding.etBirthDateLayout.error = "Выберите дату рождения"
            isValid=false
        } else {
            binding.etBirthDateLayout.error = null
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}