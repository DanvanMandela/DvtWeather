package com.example.dvtweather.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.dvtweather.R
import com.example.dvtweather.data.entity.favourite.FavoriteEntity
import com.example.dvtweather.data.entity.favourite.FavoriteItem
import com.example.dvtweather.data.entity.weather.WeatherItem
import com.example.dvtweather.data.repository.favourite.FavoriteRepository
import com.example.dvtweather.data.repository.weather.WeatherRepository
import com.example.dvtweather.view.navigation.NavigationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val weatherRepository: WeatherRepository,
    private val navigationModel: NavigationModel
) : ViewModel() {
    private val loadingLayoutSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)

    private val toastMessageSubject: PublishSubject<Int> = PublishSubject.create()

    val toastMessage: Observable<Int>
        get() = toastMessageSubject.hide()
    val loadingUi: Observable<Boolean>
        get() = loadingLayoutSubject.hide()


    fun getFavorites(): Single<List<FavoriteEntity>> {
        return favoriteRepository.getFavourite()
            .doOnSubscribe { loadingLayoutSubject.onNext(true) }
            .doOnSuccess { loadingLayoutSubject.onNext(false) }
            .doOnError {
                toastMessageSubject.onNext(R.string.oops_no_data)
            }
            .map {
                it
            }
    }

    fun saveFavorite(data: WeatherItem) {
        weatherRepository.getWeather().map {
            if (it.id == data.id) {
                favoriteRepository.saveFavorite(
                    FavoriteEntity(
                        name = data.name,
                        coordinate = it.coordinate,
                        temp = data.current,
                        icon = data.icon,
                        weather = data.desc
                    )
                )
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    suspend fun checkExist(name: String): Boolean {
        return favoriteRepository.exists(name)
    }

    fun observeFavorite(): Observable<List<FavoriteEntity>> {
        return favoriteRepository.observeFavorite().map { it }
    }

    fun getFavoriteItem(): Observable<List<FavoriteItem>> {
        return favoriteRepository.observeFavorite().map { favoriteItem(it) }
    }

    private fun favoriteItem(it: List<FavoriteEntity>): List<FavoriteItem> {
        val item = mutableListOf<FavoriteItem>()
        it.forEach { data ->
            item.add(FavoriteItem(data, navigationModel.navigateToMaps(data)))
        }
        return item
    }

    fun getNavigateToFavorite(): NavDirections {
        return navigationModel.navigateToFavourite()
    }

}