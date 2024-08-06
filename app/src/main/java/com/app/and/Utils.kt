package com.app.and

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationManagerCompat

object Utils {

    const val PLAY_ACTION = "com.app.and.play"
    const val PAUSE_ACTION = "com.app.and.pause"
    const val POST_NOTIFICATION_REQUEST_CODE = 1
    const val NOTIFICATION_ID = "id"
    const val NOTIFICATION_NAME = "name"
    const val NOTIFICATION_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT
    const val baseUrl = "https://bando-radio-api.p.rapidapi.com/"


    fun saveCountry(context: Context,value : String){
        val prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("country",value)
        editor.apply()
    }
    fun getCountry(context: Context) : String? {
        val prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
        return  prefs.getString("country","")
    }
    fun setTime(context: Context,value : Boolean){
        val prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("isFirstTime",value)
        editor.apply()
    }
    fun isFirstTime(context: Context) : Boolean {
        val prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
        return  prefs.getBoolean("isFirstTime",true)
    }

    fun setMode(context: Context,value : Boolean){
        val prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("mode",value)
        editor.apply()
    }
    fun getMode(context: Context) : Boolean {
        val prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
        return  prefs.getBoolean("mode",true)
    }
    fun enabledDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun disableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}