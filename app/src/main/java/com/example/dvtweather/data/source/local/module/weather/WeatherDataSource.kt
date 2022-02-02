package com.example.dvtweather.data.source.local.module.weather

import com.example.dvtweather.data.entity.weather.ForecastEntity
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.example.dvtweather.data.entity.weather.WeatherEntity
import com.example.dvtweather.data.source.local.dao.weather.WeatherDao
import io.reactivex.Single
import java.util.*

interface WeatherDataSource : WeatherDao {

    fun fetchWeather(body: WeatherBody): Single<WeatherEntity>

    fun fetchForecast(body: WeatherBody): Single<List<ForecastEntity>>

    fun getLastUpdate(): Date
}