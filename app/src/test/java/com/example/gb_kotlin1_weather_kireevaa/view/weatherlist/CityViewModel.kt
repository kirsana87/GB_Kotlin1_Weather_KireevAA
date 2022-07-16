package com.example.gb_kotlin1_weather_kireevaa.view.weatherlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_kotlin1_weather_kireevaa.repository.Repository
import com.example.gb_kotlin1_weather_kireevaa.repository.RepositoryImpl
import com.example.gb_kotlin1_weather_kireevaa.viewmodel.MainFragment

class CityViewModel(
    private val liveDate: MutableLiveData<MainFragment.AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<MainFragment.AppState> {
        return liveDate
    }

    fun getDataWeatherRussia() =  getDataWeather(true)
    fun getDataWeatherWorld() =  getDataWeather(false)

    private fun getDataWeather(isRussian:Boolean) {
        liveDate.postValue(MainFragment.AppState.Loading)
        //if ((0..6).random() > 3) {
        if (true) {
            Thread {
                val answer =if (isRussian)repository.getRussianWeatherFromLocalStorage() else repository.getWorldWeatherFromLocalStorage()
                liveDate.postValue(MainFragment.AppState.Success(answer))

            }.start()
        } else {
            liveDate.postValue(MainFragment.AppState.Error(Throwable("Ошибка")))
        }

    }
}