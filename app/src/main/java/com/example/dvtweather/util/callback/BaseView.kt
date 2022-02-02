package com.example.dvtweather.util.callback

import android.view.View

interface BaseView {
    fun onFindViews(view: View)
    fun onBindViewModel()
}