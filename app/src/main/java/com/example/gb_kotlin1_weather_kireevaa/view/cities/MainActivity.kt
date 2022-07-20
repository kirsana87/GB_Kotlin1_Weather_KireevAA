package com.example.gb_kotlin1_weather_kireevaa.view.cities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gb_kotlin1_weather_kireevaa.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance())
                .commitNow()
        }
    }
}