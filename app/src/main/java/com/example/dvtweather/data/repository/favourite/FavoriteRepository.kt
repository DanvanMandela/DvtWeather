package com.example.dvtweather.data.repository.favourite

import com.example.dvtweather.data.entity.favourite.FavoriteEntity
import com.example.dvtweather.data.source.local.dao.favourite.FavouriteDao
import com.example.dvtweather.data.source.scope.Local
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    @param:Local private val favoriteLocalDataSource: FavouriteDao
) : FavouriteDao {


    override fun saveFavorite(data: FavoriteEntity) {
        favoriteLocalDataSource.saveFavorite(data)
    }

    override fun getFavourite(): Single<List<FavoriteEntity>> {
        return favoriteLocalDataSource.getFavourite()
    }

    override suspend fun exists(name: String): Boolean {
        return favoriteLocalDataSource.exists(name)
    }

    override fun observeFavorite(): Observable<List<FavoriteEntity>> {
        return favoriteLocalDataSource.observeFavorite()
    }
}