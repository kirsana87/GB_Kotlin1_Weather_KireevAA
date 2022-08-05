package com.example.gb_kotlin1_weather_kireevaa.model.repository

import com.example.gb_kotlin1_weather_kireevaa.MyApp
import com.example.gb_kotlin1_weather_kireevaa.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.model.room.WeatherDatabase
import com.example.gb_kotlin1_weather_kireevaa.utils.convertEntityToWeather
import com.example.gb_kotlin1_weather_kireevaa.utils.convertWeatherToEntity


class RoomRepositoryImpl: RoomDetailsRepository, RoomInsertWeather, AllWeatherFromRoom {
    override fun getWeather(lat: Double, lon: Double, callback: ResponseCallback) {
        callback.onResponse(
            WeatherDatabase.invoke(MyApp.appContext)
            .weatherDao()
            .getWeatherByCoordinates(lat, lon).let {
            convertEntityToWeather(it).last()
        })
    }

    override fun saveWeather(weather: Weather) {
        Thread {
            WeatherDatabase.invoke(MyApp.appContext).weatherDao()
                .insert(convertWeatherToEntity(weather))
        }.start()
    }

    override fun getAllWeatherFromHistory(): List<Weather> {
        return convertEntityToWeather(WeatherDatabase.invoke(MyApp.appContext).weatherDao().getAllWeather())
    }
}