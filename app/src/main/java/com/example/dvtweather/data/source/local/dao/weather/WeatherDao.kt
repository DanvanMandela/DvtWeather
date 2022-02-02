package com.example.dvtweather.data.source.local.dao.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dvtweather.data.entity.weather.ForecastEntity
import com.example.dvtweather.data.entity.weather.WeatherEntity
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeather(data: WeatherEntity)

    @Query("SELECT * FROM weather_tbl")
    fun getWeather(): Observable<WeatherEntity?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveForecast(data: ForecastEntity)

    @Query("SELECT * FROM forecast_tbl ORDER BY dt ASC")
    fun getForecast(): Observable<List<ForecastEntity>>

    @Query("DELETE FROM forecast_tbl")
    fun deleteForeCast(): Int

}