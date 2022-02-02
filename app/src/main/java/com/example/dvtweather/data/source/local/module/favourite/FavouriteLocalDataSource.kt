package com.example.dvtweather.data.source.local.module.favourite

import com.example.dvtweather.data.entity.favourite.FavoriteEntity
import com.example.dvtweather.data.source.local.dao.favourite.FavouriteDao
import com.example.dvtweather.util.scheduler.BaseSchedulerProvider
import dagger.internal.Preconditions
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteLocalDataSource @Inject constructor(
    baseSchedulerProvider: BaseSchedulerProvider,
    favouriteDao: FavouriteDao
) : FavouriteDao {

    private var favouriteDao: FavouriteDao
    private var schedulerProvider: BaseSchedulerProvider

    init {
        Preconditions.checkNotNull(baseSchedulerProvider, "scheduleProvider cannot be null")
        Preconditions.checkNotNull(favouriteDao, "favorite cannot be null")
        this.favouriteDao = favouriteDao
        this.schedulerProvider = baseSchedulerProvider
    }

    override fun saveFavorite(data: FavoriteEntity) {
        Completable.fromRunnable { favouriteDao.saveFavorite(data) }
            .subscribeOn(schedulerProvider.io()).subscribe()
    }

    override fun getFavourite(): Single<List<FavoriteEntity>> {
        return favouriteDao.getFavourite()
    }

    override suspend fun exists(name: String): Boolean {
        return favouriteDao.exists(name)
    }

    override fun observeFavorite(): Observable<List<FavoriteEntity>> {
        return favouriteDao.observeFavorite()
    }
}