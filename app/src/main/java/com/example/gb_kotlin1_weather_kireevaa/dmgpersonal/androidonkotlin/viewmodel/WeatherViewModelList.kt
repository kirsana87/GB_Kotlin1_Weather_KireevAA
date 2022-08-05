package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_kotlin1_weather_kireevaa.model.Location
import com.example.gb_kotlin1_weather_kireevaa.model.repository.RepositoryLocalImpl
import com.example.gb_kotlin1_weather_kireevaa.model.repository.RepositoryWeatherFromLocal

class WeatherViewModelList(
    private val liveData: MutableLiveData<AppStateLocal> = MutableLiveData(),
    private val repository: RepositoryWeatherFromLocal = RepositoryLocalImpl()
): ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(location: Location) = getDataFromLocalSourceList(location)

    private fun getDataFromLocalSourceList(location: Location) {
        liveData.value = AppStateLocal.Success(repository.getWeather(false, location))
    }
}