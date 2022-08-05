package com.example.gb_kotlin1_weather_kireevaa.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ccom.example.gb_kotlin1_weather_kireevaa.utils.ROOM_DB_NAME_WEATHER

@Database(entities = [WeatherEntity::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO

    companion object {
        @Volatile private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            WeatherDatabase::class.java, ROOM_DB_NAME_WEATHER)
            .build()
    }
}