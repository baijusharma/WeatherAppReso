package com.weatherappreso.baiju.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.weatherappreso.baiju.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "0eee0607b5e6bd2dab3f958f49a258db"

//http://api.weatherstack.com/current?access_key=0eee0607b5e6bd2dab3f958f49a258db&query=New%20York&%20language%20=%20en

//http://api.weatherstack.com/current?query=London&language=en&access_key=0eee0607b5e6bd2dab3f958f49a258db

interface WeatherstackAPIService {

    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String
        //  @Query("language") languageCode: String = " en "
    ): Deferred<CurrentWeatherResponse>

    // Static method access
    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): WeatherstackAPIService {

            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()


            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherstackAPIService::class.java)

        }
    }
}