package com.gb.k_2135_2136_2.model

import com.example.gb_kotlin1_weather_kireevaa.domain.Weather
import com.example.gb_kotlin1_weather_kireevaa.model.Repository


class RepositoryRemoteImpl:Repository {
    override fun getListWeather(): List<Weather> {
        Thread{
            Thread.sleep(200L)
        }.start()
        return listOf(Weather())
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(300L)
        }.start()
        return Weather()
    }
}