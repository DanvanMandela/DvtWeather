package com.example.dvtweather.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.dvtweather.data.entity.converter.WeatherBodyTypeConverter
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.example.dvtweather.data.repository.weather.worker.ForecastGETWorker
import com.example.dvtweather.data.repository.weather.worker.WeatherGETWorker
import com.example.dvtweather.data.source.worker.WorkMangerDataSource
import com.example.dvtweather.data.source.worker.WorkerCommons
import com.example.dvtweather.util.BaseClass
import com.example.dvtweather.util.connection.OnlineChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WorkViewModel @Inject constructor(
    private val worker: WorkMangerDataSource,
    private val onlineChecker: OnlineChecker
) : ViewModel() {

    fun getWeather(body: WeatherBody) {
        val weather = OneTimeWorkRequestBuilder<WeatherGETWorker>()
            .setConstraints(worker.getConstraint())
            .addTag(WorkerCommons.TAG_OUTPUT)

        weather.setInputData(
            Data.Builder()
                .putString(WorkerCommons.WEATHER_BODY, WeatherBodyTypeConverter().from(body))
                .build()
        )
        var continuation = worker.getWorkManger()
            .beginUniqueWork(
                "${WorkerCommons.WEATHER_WORKER}${BaseClass.generateAlphaNumericString(2)}",
                ExistingWorkPolicy.REPLACE,
                weather.build()
            )

        val forecast = OneTimeWorkRequestBuilder<ForecastGETWorker>()
            .setConstraints(worker.getConstraint())
            .addTag(WorkerCommons.TAG_OUTPUT)

        continuation = continuation.then(forecast.build())

        continuation.enqueue()

    }

    fun outputWorkInfo(): LiveData<List<WorkInfo>> {
        return worker.outputWorkInfo()
    }

    fun checkConnection(): Boolean {
        return onlineChecker.isOnline()
    }
}