package com.example.dvtweather.di.module

import android.app.Application
import android.content.Context
import com.example.dvtweather.data.pref.PrefModel
import com.example.dvtweather.data.pref.SharedPreferencesStorage
import com.example.dvtweather.data.source.worker.WorkManagerApp
import com.example.dvtweather.data.source.worker.WorkMangerDataSource
import com.example.dvtweather.util.connection.ConnectionListener
import com.example.dvtweather.util.connection.OnlineChecker
import com.example.dvtweather.util.provider.BaseResourceProvider
import com.example.dvtweather.util.provider.ResourceProvider
import com.example.dvtweather.view.navigation.NavGraphDirection
import com.example.dvtweather.view.navigation.NavigationModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {
    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): PrefModel

    @Binds
    abstract fun provideConnection(connectionListener: ConnectionListener): OnlineChecker

    @Binds
    abstract fun bindContext(application: Application?): Context?

    @Binds
    abstract fun bindWorkManger(work: WorkManagerApp): WorkMangerDataSource

    @Binds
    abstract fun bindResourceProvider(resourceProvider: ResourceProvider): BaseResourceProvider

    @Binds
    abstract fun provideNavigation(navGraphDirection: NavGraphDirection): NavigationModel

}