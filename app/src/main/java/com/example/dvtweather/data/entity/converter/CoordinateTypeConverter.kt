package com.example.dvtweather.data.entity.converter

import androidx.room.TypeConverter
import com.example.dvtweather.data.entity.weather.Coordinate
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class CoordinateTypeConverter {

    @TypeConverter
    fun from(coordinate: Coordinate?): String? {
        return if (coordinate == null) {
            null
        } else gsonBuilder.toJson(coordinate, Coordinate::class.java)
    }

    @TypeConverter
    fun to(coordinate: String?): Coordinate? {
        return if (coordinate == null) {
            null
        } else gsonBuilder.fromJson(coordinate, Coordinate::class.java)
    }

    companion object {
        private val gsonBuilder: Gson =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}