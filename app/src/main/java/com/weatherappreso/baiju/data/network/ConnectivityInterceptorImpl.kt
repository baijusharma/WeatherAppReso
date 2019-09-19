package com.weatherappreso.baiju.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isOnline()) {
            throw  IOException()
        } else {
            return chain.proceed(chain.request())
        }
    }

    private fun isOnline(): Boolean {
        var result = false
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager //use "as" to type cast
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.run {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }

        } else {
            @Suppress("DEPRECATION")
            connectivityManager.run {
                connectivityManager.activeNetwork?.run {
                    if (connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }

                }
            }

        }
        return result
    }
}