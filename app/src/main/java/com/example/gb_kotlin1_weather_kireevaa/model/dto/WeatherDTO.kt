package com.example.gb_kotlin1_weather_kireevaa.model.dto


import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    val fact: FactDTO,
   /* val forecast: ForecastDTO,
    val info: InfoDTO,
    val now: Int,
    @SerializedName("now_dt")
    val nowDt: String*/
)