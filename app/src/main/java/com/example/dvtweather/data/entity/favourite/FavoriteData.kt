package com.example.dvtweather.data.entity.favourite

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavDirections
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dvtweather.data.entity.converter.CoordinateTypeConverter
import com.example.dvtweather.data.entity.converter.DateTypeConverter
import com.example.dvtweather.data.entity.weather.Coordinate
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "favorite_tbl")
data class FavoriteEntity(
    @field:ColumnInfo(name = "name")
    @field:PrimaryKey()
    @field:Expose
    var name: String,

    @field:ColumnInfo(name = "coordinate")
    @field:TypeConverters(CoordinateTypeConverter::class)
    @field:Expose
    var coordinate: Coordinate,

    @field:ColumnInfo(name = "temp")
    @field:Expose
    var temp: String,


    @field:ColumnInfo(name = "weather")
    @field:Expose
    @field:StringRes
    var weather: Int,

    @field:ColumnInfo(name = "icon")
    @field:Expose
    @field:DrawableRes
    var icon: Int
):Parcelable {
    @field:ColumnInfo(name = "created_at")
    @field:TypeConverters(DateTypeConverter::class)
    @field:Expose
    var date: Date = Date()
}

data class FavoriteItem(
    var favoriteEntity: FavoriteEntity,
    var direction: NavDirections
)