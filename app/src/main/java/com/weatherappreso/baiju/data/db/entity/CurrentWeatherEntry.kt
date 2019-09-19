package com.weatherappreso.baiju.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(

    val cloudcover: Int,
    val feelslike: Double,
    val humidity: Int,
    @SerializedName("observation_time") //real name
    val observationTime: String,
    val precip: Double,
    val pressure: Int,
    val temperature: Double,
    @SerializedName("uv_index") // real name
    val uvIndex: Int,
    val visibility: Int,
    @SerializedName("weather_code")
    val weatherCode: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Int
) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}