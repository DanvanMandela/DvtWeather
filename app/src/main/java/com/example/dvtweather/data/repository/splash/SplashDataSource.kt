package com.example.dvtweather.data.repository.splash

import androidx.navigation.NavDirections

interface SplashDataSource {
    fun navigateToWeather(): NavDirections
}