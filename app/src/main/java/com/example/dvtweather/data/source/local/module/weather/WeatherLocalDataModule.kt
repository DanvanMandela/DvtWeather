package com.example.dvtweather.data.source.local.module.weather

import com.example.dvtweather.data.source.local.dao.weather.WeatherDao
import com.example.dvtweather.data.source.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class WeatherLocalDataModule {

    @Provides
    fun provideWeatherDao(db: AppDatabase): WeatherDao {
        return db.weatherDao
    }
}