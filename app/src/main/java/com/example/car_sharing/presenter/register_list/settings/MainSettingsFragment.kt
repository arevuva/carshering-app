package com.example.car_sharing.presenter.register_list.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController

import com.example.car_sharing.R
import com.example.car_sharing.databinding.FragmentMainSettingsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [mainSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class mainSettingsFragment : Fragment(), SettingsAdapter.OnSettingClickListener {

    private var _binding: FragmentMainSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onSettingClick(position: Int) {
        when (position) {
            0 -> findNavController().navigate(R.id.action_mainSettingsFragment_to_profileFragment)
//            1 -> findNavController().navigate(R.id.action_settingsFragment_to_themeFragment)
//            2 -> findNavController().navigate(R.id.action_settingsFragment_to_notificationsFragment)
//            3 -> findNavController().navigate(R.id.action_settingsFragment_to_connectCarFragment)
//            4 -> findNavController().navigate(R.id.action_settingsFragment_to_helpFragment)
//            5 -> findNavController().navigate(R.id.action_settingsFragment_to_inviteFriendFragment)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val settingsItems = listOf(
            SettingItem(R.drawable.avatar, "Иван Иванов", "ivan@mtuci.ru", 90f, 90f),
            SettingItem(R.drawable.car_phone, "Мои бронирования"),
            SettingItem(R.drawable.sun, "Тема"),
            SettingItem(R.drawable.bell, "Уведомления"),
            SettingItem(R.drawable.banknotes, "Подключить свой автомобиль"),
            SettingItem(R.drawable.quest, "Помощь"),
            SettingItem(R.drawable.letter, "Пригласить друга")
        )

        binding.settingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.settingsRecyclerView.adapter = SettingsAdapter(settingsItems,this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}