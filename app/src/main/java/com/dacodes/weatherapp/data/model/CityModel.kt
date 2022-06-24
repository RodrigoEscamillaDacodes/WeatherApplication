package com.dacodes.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName("name")
    val name : String,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("country")
    val country: String
)


