package com.example.gb_kotlin1_weather_kireevaa.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val temperature: Int,
    val feelsLike: Int,
    val icon: String
)