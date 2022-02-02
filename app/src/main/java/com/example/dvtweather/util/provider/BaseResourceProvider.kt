package com.example.dvtweather.util.provider

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.navigation.NavController


interface BaseResourceProvider {

    fun getString(@StringRes id: Int): String


    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String

    fun getDrawable(@DrawableRes resId: Int): Drawable


}