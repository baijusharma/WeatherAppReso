package com.weatherappreso.baiju

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.weatherappreso.baiju.data.db.ForecastDatabase
import com.weatherappreso.baiju.data.network.*
import com.weatherappreso.baiju.data.provider.UnitProvider
import com.weatherappreso.baiju.data.provider.UnitProviderImpl
import com.weatherappreso.baiju.data.repository.ForecastRepository
import com.weatherappreso.baiju.data.repository.ForecastRepositoryImpl
import com.weatherappreso.baiju.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))
        //from singleton is used for general passing of class object
        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        //with singleton is used for interface Implementation
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherstackAPIService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        //from provider is used for ViewModelFactory
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}