package com.example.dvtweather.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.dvtweather.data.repository.splash.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashRepository: SplashRepository
) : ViewModel() {

    fun getNavigation(): NavDirections {
        return splashRepository.navigateToWeather()
    }
}