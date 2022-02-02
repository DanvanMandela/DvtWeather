package com.example.dvtweather.data.source.remote.weather

import com.example.dvtweather.data.entity.weather.ForecastEntity
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.example.dvtweather.data.entity.weather.WeatherEntity
import com.example.dvtweather.data.source.local.module.weather.WeatherDataSource
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteDataSource @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeatherDataSource {
    override fun fetchWeather(body: WeatherBody): Single<WeatherEntity> {
        return weatherApiService.weather(lat = body.lat, lon = body.lon)
    }

    override fun fetchForecast(body: WeatherBody): Single<List<ForecastEntity>> {
        return weatherApiService.forecast(lat = body.lat, lon = body.lon).map { it.list }
    }

    override fun getLastUpdate(): Date {
        throw Exception("Not implemented")
    }

    override fun saveWeather(data: WeatherEntity) {
        throw Exception("Not implemented")
    }

    override fun getWeather(): Observable<WeatherEntity?> {
        throw Exception("Not implemented")
    }

    override fun saveForecast(data: ForecastEntity) {
        throw Exception("Not implemented")
    }

    override fun getForecast(): Observable<List<ForecastEntity>> {
        throw Exception("Not implemented")
    }

    override fun deleteForeCast(): Int {
        throw Exception("Not implemented")
    }
}