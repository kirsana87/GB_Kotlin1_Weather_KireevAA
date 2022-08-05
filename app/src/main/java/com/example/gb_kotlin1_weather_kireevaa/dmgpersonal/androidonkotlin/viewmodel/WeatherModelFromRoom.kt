package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_kotlin1_weather_kireevaa.model.City
import com.example.gb_kotlin1_weather_kireevaa.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.model.repository.ResponseCallback
import com.example.gb_kotlin1_weather_kireevaa.model.repository.RoomRepositoryImpl

class WeatherModelFromRoom(
    private val liveData: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(city: City) = getWeatherFromRoom(city)

    fun saveWeather(weather: Weather) = RoomRepositoryImpl().saveWeather(weather)


    private fun getWeatherFromRoom(city: City) {
        liveData.value = AppState.Loading
        val detailsRepositoryImpl = RoomRepositoryImpl()
        Thread {
            detailsRepositoryImpl.getWeather(city.lat, city.lon, callBack)
        }.start()
    }

    private val callBack = object : ResponseCallback {
        override fun onResponse(weather: Weather) {
            liveData.postValue(AppState.Success(weather))
        }

        override fun onFailure(e: Exception) {
            liveData.postValue(AppState.Error(e))
        }
    }

}