package com.weatherappreso.baiju.data.repository

import androidx.lifecycle.LiveData
import com.weatherappreso.baiju.data.db.entity.CurrentWeatherEntry

interface ForecastRepository {

    suspend fun getCurrentWeather(metric: Boolean): LiveData<out CurrentWeatherEntry>
}