package com.app.and.koin

import android.app.Application
import com.blongho.country_data.World
import org.koin.core.context.startKoin

class KoinApplication  : Application(){

    override fun onCreate() {
        super.onCreate()
        World.init(applicationContext)
        startKoin {
            modules(listOf(koinModule))
        }
    }

}