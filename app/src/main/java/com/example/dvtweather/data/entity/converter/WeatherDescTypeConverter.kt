package com.example.dvtweather.data.entity.converter

import androidx.room.TypeConverter
import com.example.dvtweather.data.entity.weather.WeatherDescription
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class WeatherDescTypeConverter {

    @TypeConverter
    fun from(data: String?): List<WeatherDescription?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<WeatherDescription?>?>() {}.type
        return gsonBuilder.fromJson<List<WeatherDescription?>>(data, listType)
    }

    @TypeConverter
    fun to(someObjects: List<WeatherDescription?>?): String? {
        return Gson().toJson(someObjects)
    }

    companion object {
        private val gsonBuilder: Gson =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}