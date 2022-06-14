package com.unam.pdm.gobookhunter.utilities

import android.content.Context

/**
 * Clase que guarda datos en las preferencias del teléfono.
 *
 * @property context el contexto de la app. Necesario para obtener las preferencias.
 * @property SCORE ubicación del puntaje guardado en las preferencias.
 * @constructor inicializa la instancia.
 */
class DataPersistence(private val context: Context ) {

    val SCORE = "score"

    /**
     * Guarda un valor en una ubicación dada dentro de las preferencias de la app.
     *
     * @param propertyName la ubicación en las preferencias.
     * @param value el valor a guardar
     */
    fun save( propertyName: String, value: String ){
        val preferences = this.context.getSharedPreferences(
            "GoBookHunterPreferences", Context.MODE_PRIVATE
        )
        preferences.edit().putString(propertyName, value).apply()
    }

    /**
     * Lee un valor de las preferencias de la app dada una ubicación
     *
     * @param propertyName la ubicación en las preferencias.
     * @param faultValue valor a devolver en caso de que no haya registro existente en las
     * preferencias
     * @return el valor guardado en la ubicación
     */
    fun read(propertyName: String, faultValue: String): String {
        val preferences = this.context.getSharedPreferences(
            "GoBookHunterPreferences", Context.MODE_PRIVATE
        )
        return preferences.getString(propertyName, faultValue)!!
    }
    
}