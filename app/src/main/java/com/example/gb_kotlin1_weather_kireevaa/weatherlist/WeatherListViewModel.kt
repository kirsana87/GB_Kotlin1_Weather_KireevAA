package com.example.gb_kotlin1_weather_kireevaa.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_kotlin1_weather_kireevaa.model.Repository
import com.example.gb_kotlin1_weather_kireevaa.model.RepositoryLocalImpl
import com.gb.k_2135_2136_2.model.RepositoryRemoteImpl
import com.gb.k_2135_2136_2.viewmodel.AppState
import java.lang.Thread.sleep

class WeatherListViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()) :
    ViewModel() {

    lateinit var repository: Repository

    fun getLiveData():MutableLiveData<AppState>{
        choiceRepository()
        return liveData
    }

    private fun choiceRepository(){
        repository = if(isConnection()){
            RepositoryRemoteImpl()
        }else{
            RepositoryLocalImpl()
        }
    }

    fun sentRequest() {
        //choiceRepository()
        liveData.value = AppState.Loading
        if((0..3).random()==2){ //FIXME
            liveData.postValue(AppState.Error(IllegalStateException("что-то пошлло не так")))
        }else{
            liveData.postValue(AppState.Success(repository.getWeather(55.755826, 37.617299900000035)))
        }

    }

    private fun isConnection(): Boolean {
        return false
    }

    override fun onCleared() { // TODO HW ***
        super.onCleared()
    }

    fun getWeatherFromLocalSource() {
        TODO("Not yet implemented")
    }
}