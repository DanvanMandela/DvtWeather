package com.example.dvtweather.data.entity.converter

import androidx.room.TypeConverter
import com.example.dvtweather.data.entity.weather.Wind
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class WindTypeConverter {

    @TypeConverter
    fun from(wind: Wind?): String? {
        return if (wind == null) {
            null
        } else gsonBuilder.toJson(wind, Wind::class.java)
    }

    @TypeConverter
    fun to(wind: String?): Wind? {
        return if (wind == null) {
            null
        } else gsonBuilder.fromJson(wind, Wind::class.java)
    }

    companion object {
        private val gsonBuilder: Gson =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}