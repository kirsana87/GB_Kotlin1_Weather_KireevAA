package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_kotlin1_weather_kireevaa.repository.Repository
import com.example.gb_kotlin1_weather_kireevaa.repository.RepositoryImpl
import com.example.gb_kotlin1_weather_kireevaa.repository.RepositoryIpl
import com.example.gb_kotlin1_weather_kireevaa.view.main.MainFragment


cclass MainViewModel(
private val liveDate: MutableLiveData<MainFragment.AppState> = MutableLiveData(),
private val repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<MainFragment.AppState> {
        return liveDate
    }

    fun getDataWeather() {
        liveDate.postValue(AppState.Loading)
        if ((0..6).random() > 3) {
            Thread {
                val answer = repository.getDataServer()
                liveDate.postValue(AppState.Success(answer))

            }.start()
        } else {
            liveDate.postValue(AppState.Error(Throwable("Ошибка")))
        }

    }
}