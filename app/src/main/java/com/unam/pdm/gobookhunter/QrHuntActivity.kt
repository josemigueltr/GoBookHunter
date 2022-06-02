package com.unam.pdm.gobookhunter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.unam.pdm.gobookhunter.dialogs.HintDialog
import java.util.*


class QrHuntActivity : AppCompatActivity() {

    private lateinit var barcodeView: BarcodeView

    val MATEMATICAS = "matematicas"
    val BIOLOGIA = "biologia"
    val FISICA = "fisica"
    val COMPUTACION = "computacion"
    val HEMEROTECA = "hemeroteca"
    val SOTANO = "sotano"
    val TESORO = "tesoro"

    val HINTSLIST = listOf(
        MATEMATICAS, BIOLOGIA, FISICA, COMPUTACION, HEMEROTECA, SOTANO, TESORO
    )

    private val eventoEscaneo =
        BarcodeCallback { result ->
            if (result.text == null) return@BarcodeCallback
            if (result.text in HINTSLIST) {
                HintDialog(QrHuntActivity@ this, result.text).show()
            } else {
                Toast.makeText(
                    QrHuntActivity@ this,
                    "Pista inválida. Prueba con un QR diferente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // TODO Este texto debería ser un recurso de cadena
            // TODO El texto también podría ser más descriptivo
            Toast.makeText(this, "Debe otorgar el permiso para usar la cámara",
                Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        setContentView(R.layout.activity_qr_hunt)
        barcodeView = findViewById(R.id.vista_escaner_bv)
        var formatoQr = Arrays.asList(BarcodeFormat.QR_CODE)
        barcodeView.setDecoderFactory(DefaultDecoderFactory(formatoQr))
        barcodeView.decodeContinuous(eventoEscaneo)
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        barcodeView.pause()
        super.onPause()
    }

}