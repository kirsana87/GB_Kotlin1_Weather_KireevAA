package com.example.gb_kotlin1_weather_kireevaa.model.repository

import com.example.gb_kotlin1_weather_kireevaa.model.Weather


interface RoomDetailsRepository {
    fun getWeather(
        lat: Double,
        lon: Double,
        callback: ResponseCallback
    )
}

interface AllWeatherFromRoom {
    fun getAllWeatherFromHistory(): List<Weather>
}

interface ResponseCallback {
    fun onResponse(weather: Weather)
    fun onFailure(e: Exception)
}