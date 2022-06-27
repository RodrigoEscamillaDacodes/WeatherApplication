package com.dacodes.weatherapp.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapConverter {

    @TypeConverter
    fun fromString(value: String?): Map<String?, String?>? {
        val mapType = object : TypeToken<Map<String?, String?>?>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    open fun fromStringMap(map: Map<String?, String?>?): String? {
        val gson = Gson()
        return gson.toJson(map)
    }

}