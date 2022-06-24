package com.dacodes.weatherapp.core.di

import android.content.Context
import androidx.room.Room
import com.dacodes.weatherapp.data.database.DatabaseCities
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun room(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        DatabaseCities::class.java,
        "CitiesDatabase"
    )
        .build()

    @Singleton
    @Provides
    fun daoCity(
        database: DatabaseCities
    ) = database.getCityDAO()

}
