package com.weatherappreso.baiju.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.weatherappreso.baiju.data.db.entity.CURRENT_WEATHER_ID
import com.weatherappreso.baiju.data.db.entity.CurrentWeatherEntry
import com.weatherappreso.baiju.data.db.unilocalized.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM current_weather WHERE id = ${CURRENT_WEATHER_ID}")
    fun getWeatherMetric(): LiveData<CurrentWeatherEntry>
}