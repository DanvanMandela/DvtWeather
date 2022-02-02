package com.example.dvtweather.data.entity.weather

import android.os.Parcelable
import androidx.room.*
import com.example.dvtweather.data.entity.converter.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class ForecastResponse(
    @field:SerializedName("cod")
    @field:Expose
    var cod: Int,
    @field:SerializedName("message")
    @field:Expose
    var message: Int,
    @field:SerializedName("cnt")
    @field:Expose
    var cnt: Int,
    @field:SerializedName("list")
    @field:Expose
    var list: MutableList<ForecastEntity>,

    @field:SerializedName("city")
    @field:ColumnInfo(name = "city")
    @field:TypeConverters(CityTypeConverter::class)
    @field:Expose
    var city: City,
)

data class WeatherBody(
    @field:SerializedName("lat")
    @field:Expose
    var lat: Double,

    @field:SerializedName("lon")
    @field:Expose
    var lon: Double
)

@Parcelize
data class Coordinate(
    @field:SerializedName("lon")
    @field:Expose
    var longitude: Double,

    @field:SerializedName("lat")
    @field:Expose
    var latitude: Double
) : Parcelable

data class Main(
    @field:SerializedName("temp")
    @field:Expose
    var temp: Double,

    @field:SerializedName("feels_like")
    @field:Expose
    var feelsLike: Double,

    @field:SerializedName("temp_min")
    @field:Expose
    var tempMin: Double,

    @field:SerializedName("temp_max")
    @field:Expose
    var tempMax: Double,

    @field:SerializedName("pressure")
    @field:Expose
    var pressure: Int,


    @field:SerializedName("sea_level")
    @field:Expose
    var seaLevel: Int,

    @field:SerializedName("grnd_level")
    @field:Expose
    var grdLevel: Int,

    @field:SerializedName("humidity")
    @field:Expose
    var humidity: Int,

    @field:SerializedName("temp_kf")
    @field:Expose
    var tempKf: Double,
)

data class Wind(
    @field:SerializedName("speed")
    @field:Expose
    var speed: Double,

    @field:SerializedName("deg")
    @field:Expose
    var deg: Int,

    @field:SerializedName("gust")
    @field:Expose
    var gust: Double,
)

data class Cloud(
    @field:SerializedName("all")
    @field:Expose
    var all: Int,
)

data class Sys(
    @field:SerializedName("type")
    @field:Expose
    var type: Int,

    @field:SerializedName("id")
    @field:Expose
    var id: Int,

    @field:SerializedName("country")
    @field:Expose
    var country: String,

    @field:SerializedName("sunrise")
    @field:Expose
    var sunrise: Long,

    @field:SerializedName("sunset")
    @field:Expose
    var sunset: Long,
)

data class WeatherDescription(
    @field:SerializedName("id")
    @field:Expose
    var id: Int,

    @field:SerializedName("main")
    @field:Expose
    var main: String,

    @field:SerializedName("description")
    @field:Expose
    var description: String,

    @field:SerializedName("icon")
    @field:Expose
    var icon: String,
)

data class City(
    @field:SerializedName("id")
    @field:Expose
    var id: Int,

    @field:SerializedName("name")
    @field:Expose
    var name: String,

    @field:SerializedName("coord")
    @field:Expose
    var coordinate: Coordinate,

    @field:SerializedName("country")
    @field:Expose
    var country: String,

    @field:SerializedName("population")
    @field:Expose
    var population: Long,

    @field:SerializedName("timezone")
    @field:Expose
    var timezone: Int,

    @field:SerializedName("sunrise")
    @field:Expose
    var sunrise: Long,

    @field:SerializedName("sunset")
    @field:Expose
    var sunset: Long,


    )

@Entity(tableName = "weather_tbl")
data class WeatherEntity(
    @field:SerializedName("coord")
    @field:ColumnInfo(name = "coord")
    @field:TypeConverters(CoordinateTypeConverter::class)
    @field:Expose
    var coordinate: Coordinate,

    @field:SerializedName("weather")
    @field:ColumnInfo(name = "weather")
    @field:TypeConverters(WeatherDescTypeConverter::class)
    @field:Expose
    var weather: List<WeatherDescription>,

    @field:SerializedName("base")
    @field:ColumnInfo(name = "base")
    @field:Expose
    var base: String,

    @field:SerializedName("main")
    @field:ColumnInfo(name = "main")
    @field:TypeConverters(MainTypeConverter::class)
    @field:Expose
    var main: Main,

    @field:SerializedName("visibility")
    @field:ColumnInfo(name = "visibility")
    @field:Expose
    var visibility: Int,

    @field:SerializedName("wind")
    @field:ColumnInfo(name = "wind")
    @field:TypeConverters(WindTypeConverter::class)
    @field:Expose
    var wind: Wind,

    @field:SerializedName("clouds")
    @field:ColumnInfo(name = "clouds")
    @field:TypeConverters(CloudTypeConverter::class)
    @field:Expose
    var cloud: Cloud,

    @field:SerializedName("dt")
    @field:ColumnInfo(name = "dt")
    @field:Expose
    var dt: Long,

    @field:SerializedName("sys")
    @field:ColumnInfo(name = "sys")
    @field:TypeConverters(SysTypeConverter::class)
    @field:Expose
    var sys: Sys,

    @field:SerializedName("timezone")
    @field:ColumnInfo(name = "timezone")
    @field:Expose
    var timezone: Int,


    @field:SerializedName("name")
    @field:ColumnInfo(name = "name")
    @field:Expose
    var name: String
) {
    @field:SerializedName("cod")
    @field:Expose
    @Ignore
    var cod: Int? = null

    @field:ColumnInfo(name = "weather_id")
    @field:PrimaryKey
    @field:Expose(serialize = false, deserialize = false)
    var id: Int = 101
}

@Entity(tableName = "forecast_tbl")
data class ForecastEntity(

    @field:SerializedName("weather")
    @field:ColumnInfo(name = "weather")
    @field:TypeConverters(WeatherDescTypeConverter::class)
    @field:Expose
    var weather: List<WeatherDescription>,


    @field:SerializedName("main")
    @field:ColumnInfo(name = "main")
    @field:TypeConverters(MainTypeConverter::class)
    @field:Expose
    var main: Main,

    @field:SerializedName("visibility")
    @field:ColumnInfo(name = "visibility")
    @field:Expose
    var visibility: Int,

    @field:SerializedName("wind")
    @field:ColumnInfo(name = "wind")
    @field:TypeConverters(WindTypeConverter::class)
    @field:Expose
    var wind: Wind,

    @field:SerializedName("clouds")
    @field:ColumnInfo(name = "clouds")
    @field:TypeConverters(CloudTypeConverter::class)
    @field:Expose
    var cloud: Cloud,

    @field:SerializedName("dt")
    @field:ColumnInfo(name = "dt")
    @field:PrimaryKey
    @field:Expose
    var dt: Long,

    @field:SerializedName("sys")
    @field:ColumnInfo(name = "sys")
    @field:TypeConverters(SysTypeConverter::class)
    @field:Expose
    var sys: Sys,


    @field:SerializedName("pop")
    @field:ColumnInfo(name = "pop")
    @field:Expose
    var pop: String,

    )