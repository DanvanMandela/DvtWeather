package com.example.dvtweather.data.entity.converter

import androidx.room.TypeConverter
import com.example.dvtweather.data.entity.weather.Cloud
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class CloudTypeConverter {

    @TypeConverter
    fun from(cloud: Cloud?): String? {
        return if (cloud == null) {
            null
        } else gsonBuilder.toJson(cloud, Cloud::class.java)
    }

    @TypeConverter
    fun to(cloud: String?): Cloud? {
        return if (cloud == null) {
            null
        } else gsonBuilder.fromJson(cloud, Cloud::class.java)
    }

    companion object {
        private val gsonBuilder: Gson =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}