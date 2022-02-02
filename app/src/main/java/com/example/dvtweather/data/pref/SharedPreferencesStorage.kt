package com.example.dvtweather.data.pref

import android.content.Context
import com.example.dvtweather.data.entity.converter.DateTypeConverter

import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesStorage @Inject constructor(@ApplicationContext context: Context) :
    PrefModel {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREF_NAME,
        Context.MODE_PRIVATE
    )


    companion object {
        private const val SHARED_PREF_NAME = "pref"
        private const val LAST_UPDATE = "lastUpdate"

    }

    override fun setLastUpdate(value: Date) {
        with(sharedPreferences.edit()) {
            putLong(LAST_UPDATE, DateTypeConverter.toTimestamp(value))
            apply()
        }
    }

    override fun getLastUpdate(): Date {
        return DateTypeConverter.toDate(
            sharedPreferences.getLong(
                LAST_UPDATE,
                DateTypeConverter.toTimestamp(Date())
            )
        )
    }


}