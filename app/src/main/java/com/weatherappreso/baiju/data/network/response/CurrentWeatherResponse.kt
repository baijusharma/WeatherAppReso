package com.weatherappreso.baiju.data.network.response


import com.google.gson.annotations.SerializedName
import com.weatherappreso.baiju.data.db.entity.CurrentWeatherEntry
import com.weatherappreso.baiju.data.db.entity.Location

data class CurrentWeatherResponse(

    @SerializedName("current") //real name
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)