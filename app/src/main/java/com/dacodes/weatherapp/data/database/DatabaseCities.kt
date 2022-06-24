package com.dacodes.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dacodes.weatherapp.data.database.dao.DAOCity
import com.dacodes.weatherapp.data.database.entities.CityEntity


@Database(entities = [CityEntity::class], version = 1, exportSchema = true)
abstract class DatabaseCities : RoomDatabase() {

    abstract fun getCityDAO(): DAOCity

}