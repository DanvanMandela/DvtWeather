package com.example.dvtweather.data.source.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.dvtweather.data.entity.converter.CityTypeConverter;
import com.example.dvtweather.data.entity.converter.CloudTypeConverter;
import com.example.dvtweather.data.entity.converter.CoordinateTypeConverter;
import com.example.dvtweather.data.entity.converter.DateTypeConverter;
import com.example.dvtweather.data.entity.converter.MainTypeConverter;
import com.example.dvtweather.data.entity.converter.SysTypeConverter;
import com.example.dvtweather.data.entity.converter.WeatherDescTypeConverter;
import com.example.dvtweather.data.entity.converter.WindTypeConverter;
import com.example.dvtweather.data.entity.favourite.FavoriteEntity;
import com.example.dvtweather.data.entity.weather.ForecastEntity;
import com.example.dvtweather.data.entity.weather.WeatherEntity;
import com.example.dvtweather.data.source.local.dao.favourite.FavouriteDao;
import com.example.dvtweather.data.source.local.dao.weather.WeatherDao;

@Database(entities = {
        WeatherEntity.class,
        ForecastEntity.class,
        FavoriteEntity.class
}, version = 1, exportSchema = false)
@TypeConverters({
        DateTypeConverter.class,
        CloudTypeConverter.class,
        CoordinateTypeConverter.class,
        MainTypeConverter.class,
        SysTypeConverter.class,
        WeatherDescTypeConverter.class,
        WindTypeConverter.class,
        CityTypeConverter.class
})
public abstract class AppDatabase extends RoomDatabase {

    public abstract WeatherDao getWeatherDao();

    public abstract FavouriteDao getFavouriteDao();
}
