package com.example.gb_kotlin1_weather_kireevaa.view.weatherlist

import com.example.gb_kotlin1_weather_kireevaa.repository.Weather


interface OnItemClickListener {
    abstract val FragmentListBinding: Any

    fun onItemClick(weather: Weather)
}