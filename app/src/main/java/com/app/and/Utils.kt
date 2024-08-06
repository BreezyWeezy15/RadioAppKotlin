package com.app.and

import android.content.Context

object Utils {

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
}