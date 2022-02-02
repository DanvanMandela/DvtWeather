package com.example.dvtweather.data.entity.converter

import androidx.room.TypeConverter
import com.example.dvtweather.data.entity.weather.City
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class CityTypeConverter {
    @TypeConverter
    fun from(city: City?): String? {
        return if (city == null) {
            null
        } else gsonBuilder.toJson(city, City::class.java)
    }

    @TypeConverter
    fun to(city: String?): City? {
        return if (city == null) {
            null
        } else gsonBuilder.fromJson(city, City::class.java)
    }

    companion object {
        private val gsonBuilder: Gson =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}