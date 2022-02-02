package com.example.dvtweather.data.source.remote.weather

import com.example.dvtweather.R
import com.example.dvtweather.data.source.constants.Constants
import com.example.dvtweather.data.source.remote.AuthorizationInterceptor
import com.example.dvtweather.util.provider.BaseResourceProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class WeatherRemoteDataModule {
    @Provides
    fun provideWeatherApiService(
        gson: Gson,
        resourceProvider: BaseResourceProvider
    ): WeatherApiService {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.BaseUrl.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(Constants.Timeout.connection, TimeUnit.SECONDS)
                    .writeTimeout(Constants.Timeout.write, TimeUnit.SECONDS)
                    .readTimeout(Constants.Timeout.read, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val url = chain
                            .request()
                            .url
                            .newBuilder()
                            .build()
                        chain.proceed(chain.request().newBuilder().url(url).build())
                    }
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(AuthorizationInterceptor(resourceProvider.getString(R.string.open_weather_key)))
                    .build()
            )
            .build()

        return retrofit.create(WeatherApiService::class.java)
    }

}