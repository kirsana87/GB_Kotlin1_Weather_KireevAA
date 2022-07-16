package com.example.gb_kotlin1_weather_kireevaa.repository

interface Repository {
    fun getDataWeatherServer():Weather
    fun getDataLocal():Weather
}