package com.example.dvtweather.data.entity.converter

import androidx.room.TypeConverter
import com.example.dvtweather.data.entity.weather.Main
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainTypeConverter {

    @TypeConverter
    fun from(main: Main?): String? {
        return if (main == null) {
            null
        } else gsonBuilder.toJson(main, Main::class.java)
    }

    @TypeConverter
    fun to(main: String?): Main? {
        return if (main == null) {
            null
        } else gsonBuilder.fromJson(main, Main::class.java)
    }

    companion object {
        private val gsonBuilder: Gson =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}