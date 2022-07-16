package com.example.gb_kotlin1_weather_kireevaa.repository

class RepositoryIpl:Repository {
    override fun getDataWeatherServer(): Weather {
       return Weather()
    }

    override fun getDataLocal(): Weather {
        return Weather()
    }
}