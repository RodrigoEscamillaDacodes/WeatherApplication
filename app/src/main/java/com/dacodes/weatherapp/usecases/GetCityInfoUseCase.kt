package com.dacodes.weatherapp.usecases

import com.dacodes.weatherapp.core.presentation.CoroutineUseCase
import com.dacodes.weatherapp.core.presentation.IoDispatcher
import com.dacodes.weatherapp.core.presentation.NetworkHelper
import com.dacodes.weatherapp.core.presentation.clearText
import com.dacodes.weatherapp.data.database.entities.toDataBase
import com.dacodes.weatherapp.domain.model.City
import com.dacodes.weatherapp.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class GetCityInfoUseCase @Inject constructor(
    @IoDispatcher
    dispatcher: CoroutineDispatcher,
    private val cityRepository: CityRepository,
    private val networkHelper: NetworkHelper
) : CoroutineUseCase<String, ArrayList<City>>(dispatcher){

    override suspend fun execute(params: String): ArrayList<City> {
        return if (networkHelper.isInternetOn()){
            val response = cityRepository.getCityInfo(params)
            if (response.isEmpty()) return ArrayList<City>()
            cityRepository.insertCity(response.first().toDataBase())
            response
        }else{
            val cities = cityRepository.getCityInfoFromDB(params.clearText())
            cities
        }
    }
}