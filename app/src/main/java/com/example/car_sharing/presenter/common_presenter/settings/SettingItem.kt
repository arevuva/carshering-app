package com.example.car_sharing.presenter.common_presenter.settings

data class SettingItem(
    val iconRes: Int,
    val title: String,
    val subtitle: String? = null,
    val iconWidth: Float = 40f, // Добавлено для ширины иконки
    val iconHeight: Float = 40f  // Добавлено для высоты иконки
)