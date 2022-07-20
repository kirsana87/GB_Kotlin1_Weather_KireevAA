package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_kotlin1_weather_kireevaa.model.Location
import com.example.gb_kotlin1_weather_kireevaa.model.Repository
import com.example.gb_kotlin1_weather_kireevaa.model.RepositoryImpl
import java.lang.Thread.sleep
import kotlin.random.Random

class WeatherViewModelList(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
): ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(location: Location) = getDataFromLocalSourceList(location)

    private fun getDataFromLocalSourceList(location: Location) {
        Thread {
            liveData.postValue(AppState.Loading)
            sleep(2000)
            when((0..10).random(Random(System.currentTimeMillis()))) {
                in 0..7 -> liveData.postValue(
                    AppState.Success(repository.getWeather(false, location)))
                else -> liveData.postValue(AppState.Error(Throwable()))
            }
        }.start()
    }
}