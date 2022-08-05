package com.example.gb_kotlin1_weather_kireevaa.model.repository

import com.example.gb_kotlin1_weather_kireevaa.model.Location
import com.example.gb_kotlin1_weather_kireevaa.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.model.getRussianCities
import com.example.gb_kotlin1_weather_kireevaa.model.getWorldCities


fun interface RepositoryWeatherFromLocal {
    fun getWeather(hasInternet: Boolean, location: Location): List<Weather>
}


class RepositoryLocalImpl : RepositoryWeatherFromLocal {
    override fun getWeather(
        hasInternet: Boolean,
        location: Location
    ): List<Weather> = when (hasInternet) {
            true -> getWeatherFromServer()
            else -> when (location) {
                Location.World -> getWeatherFromLocalSourceWorld()
                Location.Russia -> getWeatherFromLocalSourceRus()
            }}

    private fun getWeatherFromServer(): List<Weather> = listOf(Weather())

    private fun getWeatherFromLocalSourceRus(): List<Weather> = getRussianCities()

    private fun getWeatherFromLocalSourceWorld(): List<Weather> = getWorldCities()
}