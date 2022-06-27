package com.dacodes.weatherapp.data.repository

import com.dacodes.weatherapp.core.presentation.CoroutineDispatchers
import com.dacodes.weatherapp.data.base.Repository
import com.dacodes.weatherapp.data.database.dao.DAOCity
import com.dacodes.weatherapp.data.database.entities.CityEntity
import com.dacodes.weatherapp.domain.model.City
import com.dacodes.weatherapp.domain.model.toDomain
import com.dacodes.weatherapp.domain.network.ApiService
import com.dacodes.weatherapp.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    private val cityApi : ApiService,
    private val daoCity: DAOCity
) : CityRepository, Repository(coroutineDispatchers){

    override suspend fun getCityInfo(name: String): ArrayList<City> = safeApiCall{
        val response = cityApi.getCityInfo(name, 1, com.dacodes.weatherapp.BuildConfig.API_KEY)
        if(response.isEmpty()) ArrayList<City>()
        response.map { it.toDomain() } as ArrayList<City>
    }

    override suspend fun getCityInfoFromDB(name: String): ArrayList<City> {
        val response = daoCity.getCity(name)
        if(response.isEmpty()) ArrayList<City>()
        return response.map { it.toDomain() } as ArrayList<City>
    }

    override suspend fun insertCity(city: CityEntity) {
        daoCity.insertCity(city)
    }
}