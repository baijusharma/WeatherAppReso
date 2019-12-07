package com.weatherappreso.baiju.data.provider

import com.weatherappreso.baiju.internal.UnitSystem

interface UnitProvider {

    fun getUnitSystem(): UnitSystem
}