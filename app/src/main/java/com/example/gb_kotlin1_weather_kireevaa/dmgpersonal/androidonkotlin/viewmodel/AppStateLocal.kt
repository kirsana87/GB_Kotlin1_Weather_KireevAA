package com.example.gb_kotlin1_weather_kireevaa.viewmodel


import com.example.gb_kotlin1_weather_kireevaa.model.Weather

sealed class AppStateLocal {
    data class Success(val weatherData: List<Weather>) : AppStateLocal()
    data class Error(val error: Throwable) : AppStateLocal()
    object Loading : AppStateLocal()
}
