package com.dacodes.weatherapp.usecases

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.dacodes.weatherapp.core.presentation.CoroutineUseCase
import com.dacodes.weatherapp.core.presentation.IoDispatcher
import com.dacodes.weatherapp.data.database.entities.toDataBase
import com.dacodes.weatherapp.domain.model.City
import com.dacodes.weatherapp.domain.repository.CityRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class GetCityInfoUseCase @Inject constructor(
    @IoDispatcher
    dispatcher: CoroutineDispatcher,
    private val cityRepository: CityRepository,
    @ApplicationContext
    private val context: Context
) : CoroutineUseCase<String, ArrayList<City>>(dispatcher){

    override suspend fun execute(params: String): ArrayList<City> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return if (isConnected){
            val response = cityRepository.getCityInfo(params)
            if (response.isEmpty()) return ArrayList<City>()
            cityRepository.insertCity(response.first().toDataBase())
            response
        }else{
            val cities = cityRepository.getCityInfoFromDB(params)
            cities
        }
    }
}