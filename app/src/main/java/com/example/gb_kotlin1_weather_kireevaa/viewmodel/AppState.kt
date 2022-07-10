package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import com.example.gb_kotlin1_weather_kireevaa.domain.Weather


sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
