package com.example.gb_kotlin1_weather_kireevaa.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_kotlin1_weather_kireevaa.model.City
import com.example.gb_kotlin1_weather_kireevaa.model.RemoteRepository
import com.example.gb_kotlin1_weather_kireevaa.model.RemoteRepositoryImpl
import com.example.gb_kotlin1_weather_kireevaa.model.dto.WeatherDTO
import com.example.gb_kotlin1_weather_kireevaa.model.utils.getLines
import com.example.gb_kotlin1_weather_kireevaa.model.utils.getLines
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherDTOModel (
    private val liveDataDTO: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RemoteRepository =  RemoteRepositoryImpl()
) : ViewModel() {

    fun getLiveDataDTO() = liveDataDTO

    fun getWeather(city: City) = getWeatherFromServer(city)


    private fun getWeatherFromServer(city: City) {
        liveDataDTO.value = AppState.Loading
        requestToServer(city)

    }

    private fun requestToServer(city: City) {
        val uri =
            URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
        val connection: HttpsURLConnection?

        try {
            connection = uri.openConnection() as HttpsURLConnection
            connection.readTimeout = 10000
            connection.addRequestProperty("X-Yandex-API-Key", "64728275-aa0c-4635-9791-c1857df7ed35")

            Thread {
                try {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val weather = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                    Handler(Looper.getMainLooper()).post {
                        liveDataDTO.postValue(AppState.SuccessFromServer(weather))
                    }
                } catch (e : Exception) {
                    liveDataDTO.postValue(AppState.Error(e))
                    Log.e("@@@", "Cannot receive data from server", e)
                    e.printStackTrace()
                } finally {
                    connection.disconnect()
                }
            }.start()
        } catch (e : MalformedURLException) {
            liveDataDTO.value = AppState.Error(e)
            Log.e("@@@", "Fail URI", e)
            e.printStackTrace()
        }
    }
}