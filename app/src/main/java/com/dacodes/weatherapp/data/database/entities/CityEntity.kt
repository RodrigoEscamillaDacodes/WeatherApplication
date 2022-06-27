package com.dacodes.weatherapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dacodes.weatherapp.data.model.MapConverter
import com.dacodes.weatherapp.domain.model.City

@Entity(tableName = "CityTable")
data class CityEntity(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "localNames")
    @TypeConverters(MapConverter::class)
    val localNames: Map<String, String>,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "country")
    val country: String
)

fun City.toDataBase() = CityEntity(name, localNames, latitude, longitude, country)