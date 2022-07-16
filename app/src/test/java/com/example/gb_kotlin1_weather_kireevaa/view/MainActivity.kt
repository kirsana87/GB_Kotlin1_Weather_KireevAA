package com.example.gb_kotlin1_weather_kireevaa.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gb_kotlin1_weather_kireevaa.view.weatherlist.CityListFragment
import com.example.gb_kotlin1_weather_kireevaa.R
import ru.dw.gbkotlinweather.view.weatherlist.CityListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CityListFragment.newInstance())
                .commit()
        }
    }
}