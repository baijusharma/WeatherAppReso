package com.weatherappreso.baiju.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.weatherappreso.baiju.data.db.entity.CurrentWeatherEntry
import java.security.AccessControlContext
import java.util.concurrent.locks.Lock

@Database(
    entities = [CurrentWeatherEntry::class],
    version = 1
)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object {
        @Volatile //
        private var instance: ForecastDatabase? = null //intailize with null

        private val LOCK = Any() // dummy obj

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) { //if (instance!=null) return instance
            instance ?:buildDatabase(context).also { instance = it }

        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
            ForecastDatabase::class.java,"forecast.db")
            .build()
    }

}