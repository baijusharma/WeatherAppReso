package com.weatherappreso.baiju.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherappreso.baiju.data.provider.UnitProvider
import com.weatherappreso.baiju.data.repository.ForecastRepository
import com.weatherappreso.baiju.internal.UnitSystem
import com.weatherappreso.baiju.internal.lazyDeferred
import kotlinx.coroutines.launch

class CurrentWeatherViewModel constructor(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
