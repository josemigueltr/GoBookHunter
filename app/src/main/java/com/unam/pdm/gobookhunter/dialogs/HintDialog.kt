package com.unam.pdm.gobookhunter.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.unam.pdm.gobookhunter.QrHuntActivity
import com.unam.pdm.gobookhunter.R

/**
 * Clase que hereda de [Dialog] para mostrar los hints encontrados y el tesoro.
 *
 * @property activity actividad que invocó el diálogo
 * @property hint el hint a mostrar
 * @constructor despliega el diálogo correspondiente a los parámetros dados.
 */
class HintDialog(val activity: ComponentActivity, val hint: String): Dialog(activity) {

    /**
     * Inicializa el diálogo. Carga un layout dependiendo del *hint* dado y agrega
     * acciones a los botones del layout para navegación.
     */
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

        findViewById<Button>(R.id.button_continuar).setOnClickListener {
            this@HintDialog.dismiss()
        }

        findViewById<Button>(R.id.button_menu_principal).setOnClickListener {
            this@HintDialog.dismiss()

            if (activity is QrHuntActivity) {
                activity.finish()
            }
        }
    }
}