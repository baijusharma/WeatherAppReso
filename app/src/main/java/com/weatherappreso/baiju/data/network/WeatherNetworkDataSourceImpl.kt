package com.weatherappreso.baiju.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.weatherappreso.baiju.data.network.response.CurrentWeatherResponse
import com.weatherappreso.baiju.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(val weatherstackAPIService: WeatherstackAPIService) :
    WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = weatherstackAPIService
                .getCurrentWeather(location)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)

        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet", e)
        }
    }

}