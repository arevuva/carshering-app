package com.example.car_sharing.presenter.register_list.settings

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.car_sharing.R
import com.example.car_sharing.databinding.SettingsItemBinding

class SettingsAdapter(
    private val items: List<SettingItem>,
    private val listener: OnSettingClickListener
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    interface OnSettingClickListener {
        fun onSettingClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val binding = SettingsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SettingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class SettingsViewHolder(private val binding: SettingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSettingClick(adapterPosition)
            }
        }
        fun bind(item: SettingItem) {
            binding.settingIcon.setImageResource(item.iconRes)
            // Устанавливаем размеры иконки, если они заданы
            // Установка размеров иконки в пикселях, если заданы размеры в dp
            item.iconWidth.let { widthDp ->
                val widthPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, widthDp, Resources.getSystem().displayMetrics
                ).toInt()
                binding.settingIcon.layoutParams.width = widthPx
            }
            item.iconHeight.let { heightDp ->
                val heightPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, heightDp, Resources.getSystem().displayMetrics
                ).toInt()
                binding.settingIcon.layoutParams.height = heightPx
            }
            binding.settingTitle.text = item.title
            if (item.subtitle != null) {
                binding.settingSubtitle.visibility = View.VISIBLE
                binding.settingSubtitle.text = item.subtitle
            } else {
                binding.settingSubtitle.visibility = View.GONE
            }

            binding.settingArrow.setImageResource(R.drawable.chevron_right)

        }
    }
}