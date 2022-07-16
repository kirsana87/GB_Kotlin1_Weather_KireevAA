package com.example.gb_kotlin1_weather_kireevaa.repository

interface Repository {
    fun getDataServer(): Weather
    fun getWorldWeatherFromLocalStorage():List<Weather>
    fun getRussianWeatherFromLocalStorage():List<Weather>
}