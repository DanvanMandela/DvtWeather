package com.example.dvtweather.data.repository.favourite

import com.example.dvtweather.data.source.local.dao.favourite.FavouriteDao
import com.example.dvtweather.data.source.local.module.favourite.FavouriteLocalDataSource
import com.example.dvtweather.data.source.scope.Local
import com.example.dvtweather.util.scheduler.BaseSchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FavoriteRepositoryModule {
    @Local
    @Provides
    fun provideFavoriteLocalDataSource(
        favouriteDao: FavouriteDao,
        schedulerProvider: BaseSchedulerProvider?
    ): FavouriteDao {
        return FavouriteLocalDataSource(
            schedulerProvider!!,
            favouriteDao

        )
    }

}