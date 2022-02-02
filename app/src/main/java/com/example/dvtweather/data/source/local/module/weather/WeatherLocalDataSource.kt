package com.example.dvtweather.data.source.local.module.weather

import com.example.dvtweather.data.entity.weather.ForecastEntity
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.example.dvtweather.data.entity.weather.WeatherEntity
import com.example.dvtweather.data.source.local.dao.weather.WeatherDao
import com.example.dvtweather.util.scheduler.BaseSchedulerProvider
import dagger.internal.Preconditions
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherLocalDataSource @Inject constructor(
    weatherDao: WeatherDao,
    baseSchedulerProvider: BaseSchedulerProvider
) : WeatherDataSource {

    private var weatherDao: WeatherDao
    private var schedulerProvider: BaseSchedulerProvider

    init {
        Preconditions.checkNotNull(baseSchedulerProvider, "scheduleProvider cannot be null")
        Preconditions.checkNotNull(weatherDao, "weatherDao cannot be null")
        this.weatherDao = weatherDao
        this.schedulerProvider = baseSchedulerProvider
    }

    override fun fetchWeather(body: WeatherBody): Single<WeatherEntity> {
        throw Exception("Not implemented")
    }

    override fun fetchForecast(body: WeatherBody): Single<List<ForecastEntity>> {
        throw Exception("Not implemented")
    }

    override fun getLastUpdate(): Date {
        throw Exception("Not implemented")
    }

    override fun saveWeather(data: WeatherEntity) {
        Completable.fromRunnable { weatherDao.saveWeather(data) }
            .subscribeOn(schedulerProvider.io()).subscribe()
    }

    override fun getWeather(): Observable<WeatherEntity?> {
        return weatherDao.getWeather()
    }

    override fun saveForecast(data: ForecastEntity) {
        Completable.fromRunnable { weatherDao.saveForecast(data) }
            .subscribeOn(schedulerProvider.io()).subscribe()
    }

    override fun getForecast(): Observable<List<ForecastEntity>> {
        return weatherDao.getForecast()
    }

    override fun deleteForeCast(): Int {
        Completable.fromRunnable { weatherDao.deleteForeCast() }
            .subscribeOn(schedulerProvider.io()).subscribe()
        return 1
    }
}