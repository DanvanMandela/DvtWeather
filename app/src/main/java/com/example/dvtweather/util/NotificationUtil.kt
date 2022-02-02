package com.example.dvtweather.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dvtweather.R


abstract class NotificationUtil {

    companion object {
        @JvmField
        val DVT_NOTIFICATION_CHANNEL_NAME: CharSequence =
            "Dvt WorkManager Notifications"
        private const val DVT_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts"

        @JvmField
        val NOTIFICATION_TITLE: CharSequence = "Current Weather"
        private const val CHANNEL_ID = "Dvt_NOTIFICATION"
        private const val NOTIFICATION_ID = 1

        @JvmStatic
        fun showNotification(message: String, context: Context) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val name = DVT_NOTIFICATION_CHANNEL_NAME
                val description = DVT_NOTIFICATION_CHANNEL_DESCRIPTION
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                channel.description = description


                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

                notificationManager?.createNotificationChannel(channel)
            }


            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))

            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }

        fun closeNotification(context: Context) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager!!.cancel(NOTIFICATION_ID)
        }
    }


}