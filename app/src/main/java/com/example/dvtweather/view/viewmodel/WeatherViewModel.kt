package com.example.dvtweather.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dvtweather.R
import com.example.dvtweather.data.entity.weather.*
import com.example.dvtweather.data.repository.weather.WeatherRepository
import com.example.dvtweather.util.BaseClass
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val loadingLayoutSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    private val toastMessageSubject: PublishSubject<Int> = PublishSubject.create()
    private val noDataSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)
    private val dataSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)

    val toastMessage: Observable<Int>
        get() = toastMessageSubject.hide()
    val loadingUi: Observable<Boolean>
        get() = loadingLayoutSubject.hide()
    val noDataUi: Observable<Boolean>
        get() = noDataSubject.hide()
    val dataUi: Observable<Boolean>
        get() = dataSubject.hide()

    private fun weatherItem(it: WeatherEntity): WeatherItem? {
        return try {
            return WeatherItem(
                avatar = getAvatar(it.weather),
                max = BaseClass.getTempDegree(it.main.tempMax),
                min = BaseClass.getTempDegree(it.main.tempMin),
                current = BaseClass.getTempDegree(it.main.temp),
                desc = getDesc(it.weather),
                color = getColor(it.weather),
                updated = SimpleDateFormat(
                    "h:mm a",
                    Locale.getDefault()
                ).format(weatherRepository.getLastUpdate()),
                name = it.name,
                icon = getIcon(it.weather),
                id = it.id
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun getObservableForecast(): Observable<List<ForecastItem>> {
        return weatherRepository.getForecast()
            .doOnSubscribe {
                loadingLayoutSubject.onNext(true)
            }
            .flatMap {
                Observable.fromIterable(it).filter { data ->
                    BaseClass.getHour(Date(data.dt.times(1000))).equals("00")
                }.toList().toObservable()
            }
            .doOnNext {
            }
            .doOnError {
                toastMessageSubject.onNext(R.string.something_went_wromg)
            }.map {
                forecastItem(it)
            }
    }


    fun getObservableWeather(): Observable<WeatherItem?> {
        return weatherRepository.getWeather()
            .doOnSubscribe {
                loadingLayoutSubject.onNext(true)
            }
            .doOnComplete {
                loadingLayoutSubject.onNext(false)
            }.map { weatherItem(it) }
    }


    private fun forecastItem(it: List<ForecastEntity>): List<ForecastItem> {
        val item = mutableListOf<ForecastItem>()
        if (it.isNotEmpty())
            it.forEach {
                item.add(
                    ForecastItem(
                        id = it.dt,
                        day = BaseClass.getDayForInt(DateTime(Date(it.dt.times(1000))).dayOfWeek),
                        icon = getIcon(it.weather),
                        temp = BaseClass.getTempDegree(it.main.temp),
                        color = getColor(it.weather)
                    )
                )
            }
        return item
    }

    private fun getIcon(weather: List<WeatherDescription>): Int {
        var icon: Int? = null
        weather.forEach {
            when (it.main.lowercase(Locale.getDefault())) {
                WeatherEnum.RAINY.type -> {
                    icon = R.drawable.rain
                }
                WeatherEnum.CLOUDY.type -> {
                    icon = R.drawable.partlysunny
                }
                WeatherEnum.SUNNY.type -> {
                    icon = R.drawable.clear
                }
            }
        }

        return icon!!
    }

    private fun getColor(weather: List<WeatherDescription>): Int {
        var color: Int? = null
        weather.forEach {
            when (it.main.lowercase(Locale.getDefault())) {
                WeatherEnum.RAINY.type -> {
                    color = R.color.rainy_color
                }
                WeatherEnum.CLOUDY.type -> {
                    color = R.color.cloudy_color
                }
                WeatherEnum.SUNNY.type -> {
                    color = R.color.sunny_color
                }
            }
        }

        return color!!
    }

    private fun getDesc(weather: List<WeatherDescription>): Int {
        var desc: Int? = null
        weather.forEach {
            when (it.main.lowercase(Locale.getDefault())) {
                WeatherEnum.RAINY.type -> {
                    desc = R.string.rainy
                }
                WeatherEnum.CLOUDY.type -> {
                    desc = R.string.cloudy
                }
                WeatherEnum.SUNNY.type -> {
                    desc = R.string.sunny
                }
            }
        }

        return desc!!
    }

    private fun getAvatar(weather: List<WeatherDescription>): Int {
        var avatar: Int? = null
        weather.forEach {
            when (it.main.lowercase(Locale.getDefault())) {
                WeatherEnum.RAINY.type -> {
                    avatar = R.drawable.sea_rainy
                }
                WeatherEnum.CLOUDY.type -> {
                    avatar = R.drawable.sea_cloudy
                }
                WeatherEnum.SUNNY.type -> {
                    avatar = R.drawable.sea_sunny
                }
            }
        }

        return avatar!!
    }
}