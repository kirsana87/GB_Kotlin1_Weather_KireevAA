package com.example.gb_kotlin1_weather_kireevaa.model.repository

import com.example.gb_kotlin1_weather_kireevaa.model.dto.WeatherDTO


class RetrofitRepositoryImpl(private val remoteDataSource: RemoteDataSource): RetrofitDetailsRepository {
    override fun getWeather(lat: Double, lon: Double, callback: retrofit2.Callback<WeatherDTO>) {
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }
}