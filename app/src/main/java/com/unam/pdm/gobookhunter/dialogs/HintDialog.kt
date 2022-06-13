package com.unam.pdm.gobookhunter.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.unam.pdm.gobookhunter.R

class HintDialog(val activity: ComponentActivity, val hint: String): Dialog(activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        when (hint) {
            "matematicas" -> setContentView(R.layout.hint_dialog_math)
            "biologia" -> setContentView(R.layout.hint_dialog_biology)
            "fisica" -> setContentView(R.layout.hint_dialog_physics)
            "computacion" -> setContentView(R.layout.hint_dialog_computer)
            "hemeroteca" -> setContentView(R.layout.hint_dialog_newspaper)
            "sotano" -> setContentView(R.layout.hint_dialog_basement)
            "tesoro" -> setContentView(R.layout.hint_dialog_treasure)
            else -> { setContentView(R.layout.hint_dialog)}
        }


        //findViewById<TextView>(R.id.Pista).text = "Encontraste una pista: " + this.hint

        findViewById<Button>(R.id.button_continuar).setOnClickListener {
            this@HintDialog.dismiss()
        }

        findViewById<Button>(R.id.button_menu_principal).setOnClickListener {
            this@HintDialog.dismiss()
        }
    }
}