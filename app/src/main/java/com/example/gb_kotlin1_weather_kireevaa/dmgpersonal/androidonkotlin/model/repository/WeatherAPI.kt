package com.example.gb_kotlin1_weather_kireevaa.model.repository


import ccom.example.gb_kotlin1_weather_kireevaa.utils.YANDEX_API_KEY
import com.example.gb_kotlin1_weather_kireevaa.model.dto.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {
    @GET("v2/informers")
    fun getWeather(
        @Header(YANDEX_API_KEY) token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
}