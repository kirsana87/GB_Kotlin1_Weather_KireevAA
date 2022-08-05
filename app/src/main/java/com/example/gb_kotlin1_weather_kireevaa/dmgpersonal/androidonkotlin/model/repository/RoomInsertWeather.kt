package com.example.gb_kotlin1_weather_kireevaa.model.repository

import com.example.gb_kotlin1_weather_kireevaa.model.Weather

fun interface RoomInsertWeather {
    fun saveWeather(weather: Weather)
}