package com.dacodes.weatherapp.data.database.dao

import androidx.room.*
import com.dacodes.weatherapp.data.database.entities.CityEntity
import com.dacodes.weatherapp.data.model.CityModel

@Dao
interface DAOCity {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(model: CityEntity)

    @Query("SELECT * FROM citytable WHERE name = :cityName")
    suspend fun getCity(cityName: String): List<CityEntity>

    @Query("DELETE FROM citytable WHERE name = :cityName")
    suspend fun deleteCity(cityName: String)

}