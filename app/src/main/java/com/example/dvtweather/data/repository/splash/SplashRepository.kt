package com.example.dvtweather.data.repository.splash

import androidx.navigation.NavDirections
import com.example.dvtweather.view.navigation.NavigationModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashRepository @Inject constructor(
    private val navigationModel: NavigationModel
) : SplashDataSource {
    override fun navigateToWeather(): NavDirections {
        return navigationModel.navigateToWeather()
    }
}