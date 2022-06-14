package com.unam.pdm.gobookhunter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button

/**
 * Actividad principal. Muestra la pantalla splash y un botón para dirigirse
 * al menú principal.
 */
class SplashScreen : AppCompatActivity() {

    /**
     * Inicializa la actividad. Agrega una acción al botón *btn_start* para iniciar
     * la actividad [MainMenuActivity]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}