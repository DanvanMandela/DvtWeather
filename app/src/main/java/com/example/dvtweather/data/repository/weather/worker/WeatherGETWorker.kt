package com.example.dvtweather.data.repository.weather.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.dvtweather.data.entity.converter.WeatherBodyTypeConverter
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.example.dvtweather.data.entity.weather.WeatherEntity
import com.example.dvtweather.data.pref.PrefModel
import com.example.dvtweather.data.repository.weather.WeatherRepository
import com.example.dvtweather.data.source.worker.WorkerCommons
import com.example.dvtweather.util.NotificationUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

@HiltWorker
class WeatherGETWorker @AssistedInject constructor(
    private val weatherRepository: WeatherRepository,
    private val storage: PrefModel,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : RxWorker(context, workerParameters) {
    override fun createWork(): Single<Result> {
        return try {
            val body: WeatherBody? =
                WeatherBodyTypeConverter().to(
                    inputData.getString(
                        WorkerCommons.WEATHER_BODY
                    )
                )
            return weatherRepository.fetchWeather(body!!)
                .doOnError {
                    constructResponse(
                        Result.failure(
                            Data.Builder().putString("Error", it.localizedMessage).build()
                        )
                    )
                }
                .map {
                    //showNotification(it)
                    storage.setLastUpdate(Date())
                    weatherRepository.saveWeather(it)
                    constructResponse(
                        Result.success(
                            Data.Builder()
                                .putString(
                                    WorkerCommons.WEATHER_BODY,
                                    WeatherBodyTypeConverter().from(body)
                                )
                                .build()
                        )
                    )
                }
                .onErrorReturn { constructResponse(Result.retry()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        } catch (e: Exception) {
            Log.e("Error", e.printStackTrace().toString())
            Single.just(Result.failure())
        }
    }

    private fun showNotification(it: WeatherEntity) {
        it.weather.forEach {
            NotificationUtil.showNotification(it.description, applicationContext)
        }
    }

    private fun constructResponse(result: Result): Result {
        return result
    }
}