package com.example.car_sharing.presenter.common_presenter.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.car_sharing.R
import com.example.car_sharing.data.viewmodels.SignInViewModel
import com.example.car_sharing.data.viewmodels.SignUpViewModel
import com.example.car_sharing.databinding.FragmentMainRegBinding


class AuthFragment : Fragment() {

    // ViewBinding переменная
    private var _binding: FragmentMainRegBinding? = null
    var isValid = true
    private val binding get() = _binding!!
    private val signInViewModel: SignInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMainRegBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signInViewModel.onEmailChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.etTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Вызываем метод onEmailChange при изменении текста
                s?.let {
                    signInViewModel.onPasswordChange(it.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.btnLogin.setOnClickListener {
            validateInput()
            if (isValid) {
                signInViewModel.onSignIn()
            }
        }

        // Наблюдаем за результатом авторизации
        signInViewModel.signInResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().navigate(R.id.action_mainRegFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.action_mainRegFragment_to_registerViewPagerFragment)
        }
        // Обработчик нажатия на кнопку "Забыли пароль"
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_mainRegFragment_to_forgotPasswordFragment)

        }
    }

    private fun validateInput() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etTextPassword.text.toString().trim()

        // Сброс ошибок перед валидацией
        binding.etEmailLayout.error = null
        binding.etPasswordLayout.error = null
        isValid=true
        // Проверка электронной почты
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailLayout.error = "Некорректный адрес электронной почты"
            isValid = false
        }

        // Проверка пароля
        if (password.isEmpty()) {
            binding.etPasswordLayout.error = "Пароль не может быть пустым"
            isValid = false
        } else if (password.length < 6) {
            binding.etPasswordLayout.error = "Пароль должен содержать не менее 6 символов"
            isValid = false
        }

        if (isValid) {
            // Если данные корректны, можно продолжить
            Toast.makeText(requireContext(), "Данные корректны, можно продолжить", Toast.LENGTH_LONG).show()
        }
    }




}