package com.example.gb_kotlin1_weather_kireevaa.view

import com.example.gb_kotlin1_weather_kireevaa.repository.Weather


sealed class AppState {
    object Loading: AppState()
    data class Success(val weatherList:List<Weather>): AppState()
    data class Error(val error:Throwable): AppState()
}