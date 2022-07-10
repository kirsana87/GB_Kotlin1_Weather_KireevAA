package com.example.gb_kotlin1_weather_kireevaa.details

import com.example.gb_kotlin1_weather_kireevaa.domain.Weather


fun interface OnItemClick {
    fun onItemClick(weather: Weather)
}