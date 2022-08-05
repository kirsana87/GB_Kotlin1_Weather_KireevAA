package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import com.example.gb_kotlin1_weather_kireevaa.model.Weather


sealed class AppStateRoom {
    data class Success(val weather: List<Weather>) : AppStateRoom()
    data class Error(val error: Throwable) : AppStateRoom()
    object Loading : AppStateRoom()
}
