package com.example.gb_kotlin1_weather_kireevaa.view.cities.cities

import androidx.recyclerview.widget.DiffUtil
import com.example.gb_kotlin1_weather_kireevaa.model.Weather

class CityListDiffUtilCallback(private val oldList: List<Weather>, private val newList: List<Weather>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }
}