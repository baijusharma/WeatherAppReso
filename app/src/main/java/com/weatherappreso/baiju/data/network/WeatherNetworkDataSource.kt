package com.weatherappreso.baiju.data.network

import androidx.lifecycle.LiveData
import com.weatherappreso.baiju.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(location: String, languageCode: String)
}