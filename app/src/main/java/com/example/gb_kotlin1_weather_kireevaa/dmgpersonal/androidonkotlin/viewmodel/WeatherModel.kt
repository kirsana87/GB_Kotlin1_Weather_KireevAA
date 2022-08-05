package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ccom.example.gb_kotlin1_weather_kireevaa.utils.REQUEST_ERROR
import ccom.example.gb_kotlin1_weather_kireevaa.utils.SERVER_ERROR
import com.example.gb_kotlin1_weather_kireevaa.model.City
import com.example.gb_kotlin1_weather_kireevaa.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.model.dto.WeatherDTO
import com.example.gb_kotlin1_weather_kireevaa.model.repository.RemoteDataSource
import com.example.gb_kotlin1_weather_kireevaa.model.repository.RetrofitRepositoryImpl
import com.example.gb_kotlin1_weather_kireevaa.utils.convertDtoToWeather


class WeatherModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(city: City) = getWeatherFromServer(city)

    fun setWeather(weather: Weather) {
        liveData.postValue(AppState.Success(weather))
    }


    private fun getWeatherFromServer(city: City) {
        liveData.value = AppState.Loading
        val detailsRepositoryImpl = RetrofitRepositoryImpl(RemoteDataSource())
        detailsRepositoryImpl.getWeather(city.lat, city.lon, callBack)
    }

    private val callBack = object : retrofit2.Callback<WeatherDTO> {
        override fun onResponse(
            call: retrofit2.Call<WeatherDTO>,
            response: retrofit2.Response<WeatherDTO>
        ) {
            val serverResponse: WeatherDTO? = response.body()

            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    AppState.Success(convertDtoToWeather(serverResponse))
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }
}