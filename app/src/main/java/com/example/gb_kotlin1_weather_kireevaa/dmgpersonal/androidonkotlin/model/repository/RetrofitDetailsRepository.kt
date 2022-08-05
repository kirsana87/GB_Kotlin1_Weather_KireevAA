package com.example.gb_kotlin1_weather_kireevaa.model.repository

import com.example.gb_kotlin1_weather_kireevaa.model.dto.WeatherDTO


interface RetrofitDetailsRepository {
    fun getWeather(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    )
}