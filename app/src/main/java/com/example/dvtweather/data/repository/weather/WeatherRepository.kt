package com.example.dvtweather.data.repository.weather

import com.example.dvtweather.data.entity.weather.ForecastEntity
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.example.dvtweather.data.entity.weather.WeatherEntity
import com.example.dvtweather.data.pref.PrefModel
import com.example.dvtweather.data.source.local.module.weather.WeatherDataSource
import com.example.dvtweather.data.source.scope.Local
import com.example.dvtweather.data.source.scope.Remote
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    @param:Local private val weatherLocalDataSource: WeatherDataSource,
    @param:Remote private val weatherRemoteDataSource: WeatherDataSource,
    private val prefModel: PrefModel
) : WeatherDataSource {
    override fun fetchWeather(body: WeatherBody): Single<WeatherEntity> {
        return weatherRemoteDataSource.fetchWeather(body)
    }

    override fun fetchForecast(body: WeatherBody): Single<List<ForecastEntity>> {
        return weatherRemoteDataSource.fetchForecast(body)
    }

    override fun getLastUpdate(): Date {
        return prefModel.getLastUpdate()
    }

    override fun saveWeather(data: WeatherEntity) {
        weatherLocalDataSource.saveWeather(data)
    }

    override fun getWeather(): Observable<WeatherEntity?> {
        return weatherLocalDataSource.getWeather()
    }

    override fun saveForecast(data: ForecastEntity) {
        weatherLocalDataSource.saveForecast(data)
    }

    override fun getForecast(): Observable<List<ForecastEntity>> {
        return weatherLocalDataSource.getForecast()
    }

    override fun deleteForeCast(): Int {
        return weatherLocalDataSource.deleteForeCast()
    }
}