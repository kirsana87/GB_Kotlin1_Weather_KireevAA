package com.example.gb_kotlin1_weather_kireevaa.model.dto


import com.google.gson.annotations.SerializedName

data class FactDTO(
    @SerializedName("feels_like")
    val feelsLike: Int,
    val icon: String,
    var temp: Int,
)