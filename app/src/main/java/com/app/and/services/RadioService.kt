package com.app.and.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.app.and.Utils
import com.app.and.listeners.RadioListener

class RadioService  : Service() {


    lateinit var radioListener: RadioListener
    override fun onBind(p0: Intent?): IBinder = RadioBinder()

    inner class RadioBinder() : Binder() {
        fun  getRadioService() : RadioService = this@RadioService
    }
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra("ACTION")
        if (action == Utils.PAUSE_ACTION){
            radioListener.onPlay()
        } else {
            radioListener.onPause()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}