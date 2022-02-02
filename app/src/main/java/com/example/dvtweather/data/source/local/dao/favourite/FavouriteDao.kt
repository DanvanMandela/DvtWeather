package com.example.dvtweather.data.source.local.dao.favourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dvtweather.data.entity.favourite.FavoriteEntity
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavorite(data: FavoriteEntity)

    @Query("SELECT * FROM favorite_tbl ORDER BY created_at ASC")
    fun getFavourite(): Single<List<FavoriteEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_tbl WHERE name = :name)")
    suspend fun exists(name: String): Boolean

    @Query("SELECT * FROM favorite_tbl ORDER BY created_at ASC LIMIT 4")
    fun observeFavorite(): Observable<List<FavoriteEntity>>
}