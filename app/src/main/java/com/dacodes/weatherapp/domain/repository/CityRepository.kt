package com.dacodes.weatherapp.domain.repository

import com.dacodes.weatherapp.data.database.entities.CityEntity
import com.dacodes.weatherapp.domain.model.City

interface CityRepository {
    suspend fun getCityInfo(name: String): ArrayList<City>
    suspend fun getCityInfoFromDB(name: String): ArrayList<City>
    suspend fun insertCity(city: CityEntity)
}