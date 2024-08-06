package com.app.and.koin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.app.and.Utils
import com.blongho.country_data.World
import org.koin.core.context.startKoin

class KoinApplication  : Application(){

    override fun onCreate() {
        super.onCreate()

        if (Utils.getMode(this)){
            Utils.enabledDarkMode()
        } else {
            Utils.disableDarkMode()
        }

        World.init(applicationContext)
        startKoin {
            modules(listOf(koinModule))
        }

        createNotification()
    }

    private fun createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                Utils.NOTIFICATION_ID,
                Utils.NOTIFICATION_NAME,
                Utils.NOTIFICATION_IMPORTANCE
            )

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}