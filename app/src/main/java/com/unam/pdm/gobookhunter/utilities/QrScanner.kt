package com.unam.pdm.gobookhunter.utilities

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.unam.pdm.gobookhunter.dialogs.HintDialog

class QrScanner(activity: ComponentActivity) {

    private val barcodeLauncher: ActivityResultLauncher<ScanOptions> =
        activity.registerForActivityResult<ScanOptions, ScanIntentResult>(
            ScanContract(),
            ActivityResultCallback { result: ScanIntentResult ->
                if (result.contents == null) {
                    Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        activity,
                        "Scanned: " + result.contents,
                        Toast.LENGTH_LONG
                    ).show()
                    if (result.contents.equals("matematicas")) {
                        HintDialog(activity).show()
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