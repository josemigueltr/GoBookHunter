package com.unam.pdm.gobookhunter.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.activity.ComponentActivity
import com.journeyapps.barcodescanner.ScanOptions
import com.unam.pdm.gobookhunter.R
import com.unam.pdm.gobookhunter.utilities.QrScanner

class HintDialog(val activity: ComponentActivity): Dialog(activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_hint)

        val qrScanner = QrScanner.getInstance(this.activity)

        findViewById<Button>(R.id.button_continuar).setOnClickListener {
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            options.setPrompt("Scan a barcode")

            options.setBeepEnabled(false)
            options.setBarcodeImageEnabled(true)
            qrScanner.scann(options)
        }

        findViewById<Button>(R.id.button_menu_principal).setOnClickListener {
            this@HintDialog.dismiss()
        }
    }
}