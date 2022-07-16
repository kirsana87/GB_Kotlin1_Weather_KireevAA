package com.example.gb_kotlin1_weather_kireevaa.view.weatherlist

import androidx.recyclerview.widget.DiffUtil
import com.example.gb_kotlin1_weather_kireevaa.repository.Weather


class WeatherListDiffUtilCallback(val oldData: List<Weather>, val newData: List<Weather>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Weather = oldData.get(oldItemPosition)
        val newItem: Weather = newData.get(newItemPosition)
        return oldItem.city == newItem.city
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Weather = oldData.get(oldItemPosition)
        val newItem: Weather = newData.get(newItemPosition)
        return oldItem.feelsLike.equals(newItem.feelsLike)&& oldItem.temperature.equals(newItem.temperature)
    }
}