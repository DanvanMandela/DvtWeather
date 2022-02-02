package com.example.dvtweather.data.entity.converter

import androidx.room.TypeConverter
import com.example.dvtweather.data.entity.weather.Sys
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class SysTypeConverter {
    @TypeConverter
    fun from(sys: Sys?): String? {
        return if (sys == null) {
            null
        } else gsonBuilder.toJson(sys, Sys::class.java)
    }

    @TypeConverter
    fun to(sys: String?): Sys? {
        return if (sys == null) {
            null
        } else gsonBuilder.fromJson(sys, Sys::class.java)
    }

    companion object {
        private val gsonBuilder: Gson =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}