package com.dacodes.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("message")
    val message: String)
