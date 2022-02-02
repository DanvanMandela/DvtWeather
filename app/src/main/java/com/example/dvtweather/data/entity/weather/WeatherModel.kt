package com.example.dvtweather.data.entity.weather

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ForecastItem(
    var id: Long,
    var day: String,
    @DrawableRes
    var icon: Int,
    var temp: String,
    @ColorRes
    var color: Int
)

data class WeatherItem(
    @DrawableRes
    var avatar: Int,
    @DrawableRes
    var icon: Int,
    var max: String,
    var min: String,
    var current: String,
    @StringRes
    var desc: Int,
    @ColorRes
    var color: Int,
    var updated: String,
    var name: String,
    var id: Int
)
