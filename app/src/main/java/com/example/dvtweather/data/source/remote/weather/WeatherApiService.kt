package com.example.dvtweather.data.source.remote.weather

import com.example.dvtweather.data.entity.weather.ForecastResponse
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.example.dvtweather.data.entity.weather.WeatherEntity
import io.reactivex.Single
import retrofit2.http.*

interface WeatherApiService {
    @GET("data/2.5/weather")
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun weather(
        @Query("lat")  lat: Double,
        @Query("lon")  lon: Double,
    ): Single<WeatherEntity>

    @GET("data/2.5/forecast")
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun forecast(
        @Query("lat")  lat: Double,
        @Query("lon")  lon: Double,
    ): Single<ForecastResponse>
}