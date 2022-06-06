package com.unam.pdm.gobookhunter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.unam.pdm.gobookhunter.dialogs.HintDialog
import com.unam.pdm.gobookhunter.utilities.DataPersistence
import java.util.*


class QrHuntActivity : AppCompatActivity() {

    private lateinit var barcodeView: BarcodeView

    private var pointsCounter = 0

    val MATEMATICAS = "matematicas"
    val BIOLOGIA = "biologia"
    val FISICA = "fisica"
    val COMPUTACION = "computacion"
    val HEMEROTECA = "hemeroteca"
    val SOTANO = "sotano"
    val TESORO = "tesoro"

    val POINTS_COUNTER_KEY = "com.unam.pdm.gobookhunter.POINTS_COUNTER"

    val HINTSLIST = listOf(
        MATEMATICAS, BIOLOGIA, FISICA, COMPUTACION, HEMEROTECA, SOTANO, TESORO
    )

    val hintsFound = arrayOf(false, false, false, false, false, false, false)

    private val eventoEscaneo =
        BarcodeCallback { result ->
            val hintIndex = HINTSLIST.indexOf(result.text)
            if (result.text == null || hintsFound[hintIndex]) return@BarcodeCallback
            if (result.text in HINTSLIST) {
                hintsFound[hintIndex] = true
                HintDialog(this, result.text).show()
                val pointsEarned = if (result.text == TESORO) 500 else 100
                pointsCounter += pointsEarned
                val persistence = DataPersistence(this)
                val savedScore = Integer.parseInt(persistence.read(persistence.SCORE))
                persistence.save(persistence.SCORE,"" + (savedScore + pointsEarned))
                findViewById<TextView>(R.id.points_tv).setText(""+pointsCounter)
            } else {
                Toast.makeText(
                    this,
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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(POINTS_COUNTER_KEY, pointsCounter)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        pointsCounter = savedInstanceState.getInt(POINTS_COUNTER_KEY)
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