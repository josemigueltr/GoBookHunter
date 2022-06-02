package com.unam.pdm.gobookhunter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.unam.pdm.gobookhunter.utilities.DataPersistence

class RewardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)

        // Mostramos score guardado
        val persistence = DataPersistence(applicationContext)
        persistence.save(persistence.SCORE, "100")
        val score = Integer.parseInt(persistence.read(persistence.SCORE))
        findViewById<TextView>(R.id.score)
            .setText("Puntuación ${score}")

        findViewById<Button>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }
}