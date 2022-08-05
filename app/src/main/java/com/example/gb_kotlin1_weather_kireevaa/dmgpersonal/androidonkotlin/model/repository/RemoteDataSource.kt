package com.example.gb_kotlin1_weather_kireevaa.model.repository

import ccom.example.gb_kotlin1_weather_kireevaa.utils.YANDEX_BASE_URL
import com.example.gb_kotlin1_weather_kireevaa.BuildConfig
import com.example.gb_kotlin1_weather_kireevaa.model.dto.WeatherDTO
import javax.security.auth.callback.Callback


class RemoteDataSource {
    private val weatherApi = Retrofit.Builder()
        .baseUrl(YANDEX_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(WeatherAPI::class.java)

    fun getWeatherDetails(lat: Double, lon: Double, callback:
    Callback<WeatherDTO>
    ) {
        weatherApi.getWeather(
            BuildConfig.WEATHER_API_KEY, lat,
            lon).enqueue(callback)
    }
}