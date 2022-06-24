package com.dacodes.weatherapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dacodes.weatherapp.domain.model.City

@Entity(tableName = "CityTable")
data class CityEntity(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "country")
    val country: String
)

fun City.toDataBase() = CityEntity(name, latitude, longitude, country)