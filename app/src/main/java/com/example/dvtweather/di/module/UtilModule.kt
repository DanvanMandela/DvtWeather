package com.example.dvtweather.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dvtweather.util.scheduler.BaseSchedulerProvider
import com.example.dvtweather.data.source.constants.Constants
import com.example.dvtweather.data.source.local.database.AppDatabase
import com.example.dvtweather.util.provider.ResourceProvider
import com.example.dvtweather.util.scheduler.SchedulerProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UtilModule {
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.excludeFieldsWithoutExposeAnnotation()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            Constants.RoomDatabase.DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
        }).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider.instance
    }


}