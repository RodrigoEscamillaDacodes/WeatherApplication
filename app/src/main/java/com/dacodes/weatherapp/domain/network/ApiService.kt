package com.dacodes.weatherapp.domain.network

import com.dacodes.weatherapp.data.model.CityModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("geo/1.0/direct")
    suspend fun getCityInfo(
        @Query("q") name: String,
        @Query("limit") limit: Int,
        @Query("appid") appid: String
    ) : ArrayList<CityModel>

}