package com.unam.pdm.gobookhunter.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.journeyapps.barcodescanner.ScanOptions
import com.unam.pdm.gobookhunter.R
import com.unam.pdm.gobookhunter.utilities.QrScanner

class HintDialog(val activity: ComponentActivity, val hint: String): Dialog(activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        Toast.makeText(activity, hint, Toast.LENGTH_LONG).show()
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

        val qrScanner = QrScanner.getInstance(this.activity)

        findViewById<Button>(R.id.button_continuar).setOnClickListener {
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            options.setPrompt("Scan a barcode")
            options.setBeepEnabled(false)
            options.setBarcodeImageEnabled(true)
            qrScanner.scann(options)
            this@HintDialog.dismiss()
        }

        findViewById<Button>(R.id.button_menu_principal).setOnClickListener {
            this@HintDialog.dismiss()
        }
    }
}