package com.dacodes.weatherapp.core.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class NetworkHelper @Inject constructor(
    @ApplicationContext
    private val context: Context,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) {

    suspend fun isInternetOn(): Boolean {
        return withContext(dispatcher) {
            if (isNetworkAvailable()) {
                try {
                    val httpConnection: HttpURLConnection =
                        URL("http://clients3.google.com/generate_204")
                            .openConnection() as HttpURLConnection
                    httpConnection.setRequestProperty("User-Agent", "Android")
                    httpConnection.setRequestProperty("Connection", "close")
                    httpConnection.connectTimeout = 1500
                    httpConnection.connect()

                    httpConnection.responseCode == 204
                } catch (e: IOException) {
                    e.printStackTrace()
                    false
                }
            } else {
                false
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo ?: return false
            return activeNetwork.isConnectedOrConnecting
        }
    }

}