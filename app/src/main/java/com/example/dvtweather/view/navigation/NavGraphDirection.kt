package com.example.dvtweather.view.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.dvtweather.R
import com.example.dvtweather.data.entity.favourite.FavoriteEntity
import com.example.dvtweather.data.entity.favourite.FavoriteItem
import com.example.dvtweather.data.entity.weather.Coordinate
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavGraphDirection @Inject constructor() : NavigationModel {
    override fun navigateToWeather(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_weather_Fragment)
    }

    override fun navigateToFavourite(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_favourite_Fragment)
    }

    override fun navigateToMaps(favoriteEntity: FavoriteEntity): NavDirections {
        return object : NavDirections {
            override val actionId: Int
                get() = R.id.action_maps_dialog
            override val arguments: Bundle
                get() = mArguments()

            @Suppress("CAST_NEVER_SUCCEEDS")
            fun mArguments(): Bundle {
                val result = Bundle()
                if (Parcelable::class.java.isAssignableFrom(FavoriteEntity::class.java)) {
                    result.putParcelable("data", favoriteEntity as Parcelable)
                } else if (Serializable::class.java.isAssignableFrom(FavoriteEntity::class.java)) {
                    result.putSerializable("data", favoriteEntity as Serializable)
                }
                return result
            }
        }
    }
}