package com.example.dvtweather.view.navigation

import androidx.navigation.NavDirections
import com.example.dvtweather.data.entity.favourite.FavoriteEntity

interface NavigationModel {

    fun navigateToWeather(): NavDirections
    fun navigateToFavourite(): NavDirections
    fun navigateToMaps(favoriteEntity: FavoriteEntity): NavDirections

}