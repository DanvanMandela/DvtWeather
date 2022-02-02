package com.example.dvtweather.data.pref

import java.util.*

interface PrefModel {
    fun setLastUpdate(value: Date)
    fun getLastUpdate(): Date
}