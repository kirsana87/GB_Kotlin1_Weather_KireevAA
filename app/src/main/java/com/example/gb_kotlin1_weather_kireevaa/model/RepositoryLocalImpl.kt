package com.example.gb_kotlin1_weather_kireevaa.model

import com.example.gb_kotlin1_weather_kireevaa.domain.Weather
import com.example.gb_kotlin1_weather_kireevaa.domain.getRussianCities
import com.example.gb_kotlin1_weather_kireevaa.domain.getWorldCities

class RepositoryLocalImpl : RepositoryMany,RepositoryOne {
    override fun getListWeather(location: Location): List<Weather> {
        return when (location) {
            Location.Russian -> {
                getRussianCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}