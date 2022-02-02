package com.example.dvtweather.data.entity.converter

import androidx.room.TypeConverter
import com.example.dvtweather.data.entity.weather.Coordinate
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class WeatherBodyTypeConverter {
    @TypeConverter
    fun from(weatherBody: WeatherBody?): String? {
        return if (weatherBody == null) {
            null
        } else gsonBuilder.toJson(weatherBody, WeatherBody::class.java)
    }

    @TypeConverter
    fun to(weatherBody: String?): WeatherBody? {
        return if (weatherBody == null) {
            null
        } else gsonBuilder.fromJson(weatherBody, WeatherBody::class.java)
    }

    companion object {
        private val gsonBuilder: Gson =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}