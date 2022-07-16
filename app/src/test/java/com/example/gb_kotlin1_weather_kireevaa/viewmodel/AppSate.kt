package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import com.example.gb_kotlin1_weather_kireevaa.repository.Weather


sealed class AppSate {
    object Loading:AppSate()
    data class Success(val data: Weather):AppSate()
    data class Error(val error: Throwable):AppSate()
}