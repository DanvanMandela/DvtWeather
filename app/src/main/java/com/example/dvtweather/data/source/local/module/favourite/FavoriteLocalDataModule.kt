package com.example.dvtweather.data.source.local.module.favourite

import com.example.dvtweather.data.source.local.dao.favourite.FavouriteDao
import com.example.dvtweather.data.source.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FavoriteLocalDataModule {
    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavouriteDao {
        return db.favouriteDao
    }
}