package com.unam.pdm.gobookhunter.utilities

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.unam.pdm.gobookhunter.dialogs.HintDialog

/**
 * @deprecated - Reemplazada por QrHuntActivity
 */
class QrScanner(activity: ComponentActivity) {

    val MATEMATICAS = "matematicas"
    val BIOLOGIA = "biologia"
    val FISICA = "fisica"
    val COMPUTACION = "computacion"
    val HEMEROTECA = "hemeroteca"
    val SOTANO = "sotano"
    val TESORO = "tesoro"

    val HINTSLIST = listOf(MATEMATICAS, BIOLOGIA, FISICA, COMPUTACION, HEMEROTECA, SOTANO, TESORO)

    private val barcodeLauncher: ActivityResultLauncher<ScanOptions> =
        activity.registerForActivityResult<ScanOptions, ScanIntentResult>(
            ScanContract(),
            ActivityResultCallback { result: ScanIntentResult ->
                if (result.contents == null) {
                    Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    if (result.contents in HINTSLIST) {
                        HintDialog(activity, result.contents).show()
                    } else {
                        Toast.makeText(
                            activity,
                            "Pista inválida. Prueba con un QR diferente",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })

    companion object {
        var scanner: QrScanner? = null

        fun getInstance(activity: ComponentActivity): QrScanner {
            if (scanner == null)
                scanner = QrScanner(activity)
            return scanner!!
        }
    }

    fun scann(options: ScanOptions) {
        barcodeLauncher.launch(options)
    }
}