package com.example.gb_kotlin1_weather_kireevaa.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import ccom.example.gb_kotlin1_weather_kireevaa.utils.REQUEST_CODE_READ_CONTACTS
import com.example.gb_kotlin1_weather_kireevaa.MyApp
import com.example.gb_kotlin1_weather_kireevaa.model.City
import com.example.gb_kotlin1_weather_kireevaa.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.model.dto.WeatherDTO
import com.example.gb_kotlin1_weather_kireevaa.model.getAddress
import com.example.gb_kotlin1_weather_kireevaa.model.room.WeatherEntity


fun convertDtoToWeather(weatherDTO: WeatherDTO): Weather {
    return Weather(getAddress(weatherDTO.info.lat, weatherDTO.info.lon),
        temperature = weatherDTO.fact.temp,
        feelsLike = weatherDTO.fact.feelsLike,
        icon = weatherDTO.fact.icon
    )
}

fun convertWeatherToEntity(weather: Weather): WeatherEntity {
    return weather.let {
        WeatherEntity(0, it.city.name, it.city.lat, it.city.lon,
            it.temperature, it.feelsLike, it.icon)
    }
}

fun convertEntityToWeather(entity: List<WeatherEntity>): List<Weather> {
    return entity.map {
        Weather(City(it.name, it.lat, it.lon), it.temperature, it.feelsLike, it.icon)
    }
}

fun checkPermission(activity: Activity, permission: String, title: String, message: String): Boolean {
    val permResult =
        ContextCompat.checkSelfPermission(MyApp.appContext, permission)
    if (shouldShowRequestPermissionRationale(activity, permission)) {
        AlertDialog.Builder(MyApp.appContext)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Предоставить доступ") { _, _ ->
                permissionRequest(activity, permission)
            }
            .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    } else if (permResult != PackageManager.PERMISSION_GRANTED) {
        permissionRequest(activity, permission)
    } else {
        return true
    }
    return false
}

private fun permissionRequest(activity: Activity, permission: String) {
    requestPermissions(activity, arrayOf(permission), REQUEST_CODE_READ_CONTACTS)
}

fun hideKeyboard(view: View) {
    val inputMethodManager = MyApp.appContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
