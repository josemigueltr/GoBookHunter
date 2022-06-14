package com.unam.pdm.gobookhunter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.unam.pdm.gobookhunter.utilities.DataPersistence



/**
 * Clase correspondiente a la actividad de mis recompensas.
 */
class RewardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)

        // Carga y muestra el puntuaje guardado en la persistencia.
        val persistence = DataPersistence(applicationContext)
        val score = Integer.parseInt(persistence.read(persistence.SCORE, "0"))
        findViewById<TextView>(R.id.score)
            .setText("${score}")
        // Agrega un listener al boton de regreso al menu principal.
        findViewById<Button>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }
}
