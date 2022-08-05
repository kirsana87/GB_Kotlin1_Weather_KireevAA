package com.example.gb_kotlin1_weather_kireevaa.model

import android.location.Geocoder
import android.os.Parcelable
import android.util.Log
import com.example.gb_kotlin1_weather_kireevaa.MyApp
import kotlinx.android.parcel.Parcelize
import java.io.IOException

@Parcelize
data class City(
    val name: String = "Default city",
    val lat: Double,
    val lon: Double
) : Parcelable

fun getAddress(lat: Double, lon: Double): City {
    val geocoder = Geocoder(MyApp.appContext)
    var currentCityName: City
    try {
        val list = geocoder.getFromLocation(lat, lon, 1).last()
        currentCityName =
            if(list.locality != null)
                { City(list.locality, lat, lon) }
            else
                { City(list.getAddressLine(0), lat, lon) }
    } catch (e: IOException) {
        currentCityName = getDefaultCity()
        Log.d("@@@", e.toString())
    }

    return currentCityName
}

fun getCoordinates(cityName: String): City {
    val geocoder = Geocoder(MyApp.appContext)
    var currentLocation = City(lat = -1.0, lon = -1.0)
    try {
        val location = geocoder.getFromLocationName(cityName, 1)
        if(location.isNotEmpty()) {
            currentLocation = City(cityName, location.first().latitude, location.first().longitude)
        }
    } catch (e: IOException) {
        Log.d("@@@", e.toString())
    }
    return currentLocation
}