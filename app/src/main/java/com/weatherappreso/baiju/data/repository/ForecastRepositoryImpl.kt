package com.weatherappreso.baiju.data.repository

import androidx.lifecycle.LiveData
import com.weatherappreso.baiju.data.db.CurrentWeatherDao
import com.weatherappreso.baiju.data.db.entity.CurrentWeatherEntry
import com.weatherappreso.baiju.data.network.WeatherNetworkDataSource
import com.weatherappreso.baiju.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out CurrentWeatherEntry> {
        //coroutine "withContext" return a value and "launch" does not return a value
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric)
                currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherMetric()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        // no worries if u launch coroutine on global scope inside the Repository
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData() {
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather("London",Locale.getDefault().language)
    }

    private fun isFetchCurrentNeeded(lastTimeFetch: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastTimeFetch.isBefore(thirtyMinutesAgo)
    }

}