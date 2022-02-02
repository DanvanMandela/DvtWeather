package com.example.dvtweather.data.repository.weather

import com.example.dvtweather.data.source.local.dao.weather.WeatherDao
import com.example.dvtweather.data.source.local.module.weather.WeatherDataSource
import com.example.dvtweather.data.source.local.module.weather.WeatherLocalDataSource
import com.example.dvtweather.data.source.remote.weather.WeatherApiService
import com.example.dvtweather.data.source.remote.weather.WeatherRemoteDataSource
import com.example.dvtweather.data.source.scope.Local
import com.example.dvtweather.data.source.scope.Remote
import com.example.dvtweather.util.scheduler.BaseSchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class WeatherRepositoryModule {

    @Local
    @Provides
    fun provideWeatherLocalDataSource(
        weatherDao: WeatherDao,
        schedulerProvider: BaseSchedulerProvider?
    ): WeatherDataSource {
        return WeatherLocalDataSource(
            weatherDao,
            schedulerProvider!!
        )
    }


    @Remote
    @Provides
    fun provideWeatherRemoteDataSource(
        weatherApiService: WeatherApiService?,
    ): WeatherDataSource {
        return WeatherRemoteDataSource(
            weatherApiService!!
        )
    }
}