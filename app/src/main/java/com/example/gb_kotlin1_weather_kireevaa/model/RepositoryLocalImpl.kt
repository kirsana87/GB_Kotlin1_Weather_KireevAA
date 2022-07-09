package com.example.gb_kotlin1_weather_kireevaa.model

import com.example.gb_kotlin1_weather_kireevaa.domain.Weather

class RepositoryLocalImpl:Repository {
    override fun getListWeather(): List<Weather> {
        return listOf(Weather())
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}