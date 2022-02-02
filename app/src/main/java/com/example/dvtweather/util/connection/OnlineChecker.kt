package com.example.dvtweather.util.connection

/**
 * Simple interface that contains online/offline state indicator
 */
interface OnlineChecker {
    fun isOnline(): Boolean
}