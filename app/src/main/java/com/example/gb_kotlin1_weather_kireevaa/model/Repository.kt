package com.example.gb_kotlin1_weather_kireevaa.model

import com.example.gb_kotlin1_weather_kireevaa.domain.Weather


interface Repository {
    fun getListWeather():List<Weather>
    fun getWeather( lat: Double, lon: Double):Weather
}