package com.example.gb_kotlin1_weather_kireevaa.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

    @Parcelize
data class City(
    val name: String,
    val lat: Double,
    val lon: Double
) : Parcelable