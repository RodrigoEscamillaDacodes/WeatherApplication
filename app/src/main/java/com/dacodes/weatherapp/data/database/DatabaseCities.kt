package com.dacodes.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dacodes.weatherapp.data.database.dao.DAOCity
import com.dacodes.weatherapp.data.database.entities.CityEntity
import com.dacodes.weatherapp.data.model.MapConverter


@Database(entities = [CityEntity::class], version = 1, exportSchema = true)
@TypeConverters(MapConverter::class)
abstract class DatabaseCities : RoomDatabase() {

    abstract fun getCityDAO(): DAOCity

}