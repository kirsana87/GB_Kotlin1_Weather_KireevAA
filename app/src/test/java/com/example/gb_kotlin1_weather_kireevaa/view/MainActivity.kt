package com.example.gb_kotlin1_weather_kireevaa.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gb_kotlin1_weather_kireevaa.view.main.MainFragment
import com.example.gb_kotlin1_weather_kireevaa.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commit()
        }
    }
}