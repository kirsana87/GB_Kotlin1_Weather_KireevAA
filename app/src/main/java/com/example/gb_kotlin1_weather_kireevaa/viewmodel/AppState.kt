package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import com.example.gb_kotlin1_weather_kireevaa.model.Weather


sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
