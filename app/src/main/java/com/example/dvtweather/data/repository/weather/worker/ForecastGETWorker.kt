package com.example.dvtweather.data.repository.weather.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.dvtweather.data.entity.converter.WeatherBodyTypeConverter
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.example.dvtweather.data.repository.weather.WeatherRepository
import com.example.dvtweather.data.source.worker.WorkerCommons
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@HiltWorker
class ForecastGETWorker @AssistedInject constructor(
    private val weatherRepository: WeatherRepository,
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
            return weatherRepository.fetchForecast(body!!)
                .doOnError {
                    constructResponse(
                        Result.failure(
                            Data.Builder().putString("Error", it.localizedMessage).build()
                        )
                    )
                }
                .map {
                    weatherRepository.deleteForeCast()
                    it.forEach { data ->
                        weatherRepository.saveForecast(data)
                    }
                    constructResponse(
                        Result.success(
                            Data.Builder()
                                .putBoolean(
                                    WorkerCommons.SUCCESS, true
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

    private fun constructResponse(result: Result): Result {
        return result
    }
}