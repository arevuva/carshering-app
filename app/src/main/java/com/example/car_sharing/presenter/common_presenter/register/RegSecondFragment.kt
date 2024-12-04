package com.example.car_sharing.presenter.common_presenter.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.data.viewmodels.SignUpViewModel
import com.example.car_sharing.databinding.FragmentRegSecondBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegSecondFragment : Fragment() {
    private var _binding: FragmentRegSecondBinding? = null
    private val binding get() = _binding!!
    private var isValid=true
    private val calendar = Calendar.getInstance()
    private val signUpViewModel: SignUpViewModel by activityViewModels()

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

        binding.etFirstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signUpViewModel.onFirstNameChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.etMiddleName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signUpViewModel.onSecondNameChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etLastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signUpViewModel.onThirdNameChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etBirthDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signUpViewModel.onDateBirthChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            val gender = when (checkedId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                else -> null
            }
            gender?.let { signUpViewModel.onGenderChange(it) }
        }

        // Обработчик нажатия на кнопку "Далее"
        binding.btnNextReg2.setOnClickListener {
            val lastName = binding.etLastName.text.toString().trim()
            val firstName = binding.etFirstName.text.toString().trim()
            val middleName = binding.etMiddleName.text.toString().trim()
            val birthDate = binding.etBirthDate.text.toString().trim()
            val gender = if (binding.rbMale.isChecked) "Мужской" else "Женский"
            signUpViewModel.onFirstNameChange(firstName)
            signUpViewModel.onSecondNameChange(middleName)
            signUpViewModel.onThirdNameChange(lastName)
            signUpViewModel.onDateBirthChange(birthDate)
            signUpViewModel.onGenderChange(gender)

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