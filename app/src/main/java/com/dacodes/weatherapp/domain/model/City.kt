package com.dacodes.weatherapp.domain.model

import com.dacodes.weatherapp.data.database.entities.CityEntity
import com.dacodes.weatherapp.data.model.CityModel

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String
)

fun CityModel.toDomain() = City(name, latitude, longitude, country)
fun CityEntity.toDomain() = City(name, latitude, longitude, country)