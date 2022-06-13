package com.unam.pdm.gobookhunter.utilities

import android.content.Context

class DataPersistence(private val context: Context ) {

    val SCORE = "score"

    fun save( propertyName: String, value: String ){
        val preferences = this.context.getSharedPreferences( "GoBookHunterPreferences", Context.MODE_PRIVATE)
        preferences.edit().putString(propertyName, value).apply()
    }

    fun read(propertyName: String, faultValue: String): String {
        val preferences = this.context.getSharedPreferences( "GoBookHunterPreferences", Context.MODE_PRIVATE)
        return preferences.getString(propertyName, faultValue)!!
    }
    
}