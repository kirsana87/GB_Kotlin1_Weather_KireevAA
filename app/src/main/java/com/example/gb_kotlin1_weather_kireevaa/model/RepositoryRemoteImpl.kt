package com.gb.k_2135_2136_2.model

import com.example.gb_kotlin1_weather_kireevaa.domain.Weather
import com.example.gb_kotlin1_weather_kireevaa.model.RepositoryOne


class RepositoryRemoteImpl: RepositoryOne {

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(300L)
        }.start()
        return Weather()
    }
}