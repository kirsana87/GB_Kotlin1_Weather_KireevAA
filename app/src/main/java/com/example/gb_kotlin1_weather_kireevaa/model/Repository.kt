package com.example.gb_kotlin1_weather_kireevaa.model

import com.example.gb_kotlin1_weather_kireevaa.model.dto.WeatherDTO
import java.io.BufferedReader
import java.util.stream.Collectors

fun interface Repository {

    fun getWeather(hasInternet: Boolean, location: Location): List<Weather>
}

fun interface RemoteRepository {
    fun getWeather(city: City) : WeatherDTO
}

class RemoteRepositoryImpl : RemoteRepository {
    override fun getWeather(city: City): WeatherDTO {
        TODO("Not yet implemented")
    }

}

class RepositoryImpl : Repository {


    override fun getWeather(
        hasInternet: Boolean,
        location: Location): List<Weather> = when (hasInternet) {
        true -> getWeatherFromServer(location)
        else -> when (location) {
            Location.World -> getWeatherFromLocalSourceWorld()
            Location.Russia -> getWeatherFromLocalSourceRus()
        }}


    //список
    private fun getWeatherFromServer(location: Location): List<Weather> = listOf(Weather())

    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun getWeatherFromLocalSourceRus(): List<Weather> = getRussianCities()

    private fun getWeatherFromLocalSourceWorld(): List<Weather> = getWorldCities()
}