package com.example.car_sharing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.car_sharing.databinding.FragmentMainRegBinding
import com.example.car_sharing.databinding.FragmentPreviewRegBinding


class MainRegFragment : Fragment() {

    // ViewBinding переменная
    private var _binding: FragmentMainRegBinding? = null
    var isValid = true
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMainRegBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        binding.btnLogin.setOnClickListener {
            validateInput()
            if(isValid){
                findNavController().navigate(R.id.action_mainRegFragment_to_homeFragment)
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
        val password = binding.editTextPassword.text.toString().trim()

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