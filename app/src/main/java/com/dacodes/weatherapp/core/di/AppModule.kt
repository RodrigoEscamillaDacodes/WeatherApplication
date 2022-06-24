package com.dacodes.weatherapp.core.di

import android.content.Context
import com.dacodes.weatherapp.BuildConfig
import com.dacodes.weatherapp.R
import com.dacodes.weatherapp.core.presentation.*
import com.dacodes.weatherapp.data.database.dao.DAOCity
import com.dacodes.weatherapp.data.repository.CityRepositoryImpl
import com.dacodes.weatherapp.domain.network.ApiService
import com.dacodes.weatherapp.domain.network.ServiceInterceptor
import com.dacodes.weatherapp.domain.repository.CityRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @DefaultDispatcher
    fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    @IoDispatcher
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @MainDispatcher
    fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    @ImmediateDispatcher
    fun immediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @Provides
    @Singleton
    fun okHttpClient(
        serviceInterceptor: ServiceInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1000, TimeUnit.MINUTES)
        .writeTimeout(1000, TimeUnit.MINUTES)
        .readTimeout(1000, TimeUnit.MINUTES)
        .retryOnConnectionFailure(false)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
            }
            addInterceptor(serviceInterceptor)
        }.build()

    @Provides
    @Singleton
    fun cityApi(
        client: OkHttpClient,
        @ApplicationContext
        context: Context
    ): ApiService =
        Retrofit.Builder()
            .client(client)
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun cityRepository(
        coroutineDispatchers: CoroutineDispatchers,
        cityApi: ApiService,
        @ApplicationContext
        context: Context,
        daoCity: DAOCity
    ): CityRepository = CityRepositoryImpl(
        context,
        coroutineDispatchers,
        cityApi,
        daoCity
    )

}
