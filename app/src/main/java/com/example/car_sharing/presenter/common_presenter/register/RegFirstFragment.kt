package com.example.car_sharing.presenter.common_presenter.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.car_sharing.R
import com.example.car_sharing.data.viewmodels.SignUpViewModel
import com.example.car_sharing.databinding.FragmentRegFirstBinding


class RegFirstFragment : Fragment() {
    private var _binding: FragmentRegFirstBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    var isValid = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentRegFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signUpViewModel.onEmailChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.etTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signUpViewModel.onPasswordChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.btnNextReg1.setOnClickListener{
            validateInput()
            if(isValid) {
                activity?.findViewById<ViewPager2>(R.id.vpreg2)?.currentItem = 1
            }
        }

        binding.gobackreg1.setOnClickListener{
        }
    }
    private fun validateInput(){
        val email = binding.etEmail.text.toString()
        val password = binding.etTextPassword.text.toString()
        val confirmPassword = binding.etConfrimTextPassword.text.toString()
        val isAgreeChecked = binding.cbAgree.isChecked
        // Сброс ошибок перед валидацией
        binding.etEmailLayout.error = null
        binding.etPasswordLayout.error = null
        binding.etConfrimTextPassword.error=null
        isValid=true

        // Проверка электронной почты
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailLayout.error = "Некорректный адрес электронной почты"
            isValid = false
        }

        // Проверка пароля
        if (password.isEmpty() || password.length < 6) {
            binding.etPasswordLayout.error = "Пароль должен содержать не менее 6 символов"
            isValid = false
        }
        // Проверка совпадения паролей
        if (password != confirmPassword) {
            binding.lConfirmPassword.error = "Пароли не совпадают"
            isValid = false
        }

        // Проверка, что пользователь согласен с условиями
        if (!isAgreeChecked) {
            Toast.makeText(requireContext(), "Необходимо согласиться с условиями обслуживания", Toast.LENGTH_LONG).show()
            isValid = false
        }
        if (isValid) {
            // Если все данные корректны, можно продолжить
            Toast.makeText(requireContext(), "Данные корректны", Toast.LENGTH_LONG).show()
        }


    }

}