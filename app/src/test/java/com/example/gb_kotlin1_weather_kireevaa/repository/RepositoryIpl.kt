package com.example.gb_kotlin1_weather_kireevaa.repository

abstract class RepositoryImpl : Repository {
    fun getDataServer(): Weather {
        Thread.sleep(2000L)
        return Weather()
    }

    override fun getDataLocal(): Weather {
        Thread.sleep(300L)
        return Weather()
    }
}