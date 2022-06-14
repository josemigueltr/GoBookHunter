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

/**
 * Clase que muestra la función de lector QR, así como datos relacionados a la búsqueda del tesoro
 *
 * @property bardcodeView el lector QR
 * @property pointsCounter contador de puntos ganados hasta el momento
 * @property currentHint hint actual encontrado
 * @property POINTS_COUNTER_KEY ubicación para guardar [pointsCounter] en caché
 * @property CURRENT_HINT_KEY ubicación para guardar [currentHint] en caché
 * @property HINTSLIST lista de hints a encontrar, en orden de búsqueda
 * @property hintsFound arreglo que guarda si un hint ha sido encontrado según su índice en
 * [HINTSLIST]
 * @property eventoEscaneo acción que se ejecuta cuando un QR es leído
 */
class QrHuntActivity : AppCompatActivity() {

    private lateinit var barcodeView: BarcodeView

    private var pointsCounter = 0
    private var currentHint = "";

    val MATEMATICAS = "matematicas"
    val BIOLOGIA = "biologia"
    val FISICA = "fisica"
    val COMPUTACION = "computacion"
    val HEMEROTECA = "hemeroteca"
    val SOTANO = "sotano"
    val TESORO = "tesoro"

    val POINTS_COUNTER_KEY = "com.unam.pdm.gobookhunter.POINTS_COUNTER"
    val CURRENT_HINT_KEY = "com.unam.pdm.gobookhunter.CURRENT_HINT_KEY"

    val HINTSLIST = listOf(
        MATEMATICAS, BIOLOGIA, FISICA, COMPUTACION, HEMEROTECA, SOTANO, TESORO
    )

    val hintsFound = arrayOf(false, false, false, false, false, false, false)

    private val eventoEscaneo =
        BarcodeCallback { result ->
            // Obtiene el index de la pista dentro de la lista de pistas posibles.
            val hintIndex = HINTSLIST.indexOf(result.text)

            // Si leo un QR inválido, muestro un mensaje
            if (hintIndex == -1) {
                Toast.makeText(
                    this,
                    "Pista inválida. Prueba con un QR diferente",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Si el resultado del escaneo es nulo o la pista ya había sido escaneada regresa.
            if (result.text == null || hintsFound[hintIndex]) return@BarcodeCallback

            // De lo contrario checa si el resultado está en la lista de pistas posibles.
            if (result.text in HINTSLIST) {
                // Marca la pista como ya escaneada ( para evitar multiples escaneos ).
                hintsFound[hintIndex] = true
                // Muestra un HintDialog que recibe el nombre de la pista escaneada.
                HintDialog(this, result.text).show()
                // Obtiene cuantos puntos debe dar la pista escaneada y los suma.
                val pointsEarned = if (result.text == TESORO) 500 else 100
                pointsCounter += pointsEarned

                // Guarda el puntuaje en la persistencia de datos.
                val persistence = DataPersistence(this)
                val savedScore = Integer.parseInt(persistence.read(persistence.SCORE, "0") )
                persistence.save(persistence.SCORE,"" + (savedScore + pointsEarned))

                // Actualiza el dialogo de la pista actual
                updateCurrentHintDialog(result.text)

                // Actualiza el indicador de puntuaje
                findViewById<TextView>(R.id.points).setText(""+pointsCounter)
            } else {
                Toast.makeText(
                    this,
                    "Pista inválida. Prueba con un QR diferente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    /**
     * Actualiza el hint a mostrar en la actividad.
     * @param hintName el nombre del hint encontrado.
     */
    fun updateCurrentHintDialog(hintName: String){
        when (hintName) {
            "matematicas" -> currentHint =
                "Piensa en tu animal favorito. Seguramente en esta zona encontrarás un libro sobre él."
            "biologia" -> currentHint =
                "A toda acción, corresponde una reacción. Si lo piensas bien, llegarás a esta ubicación."
            "fisica" -> currentHint =
                "Sólo hay 10 tipos de personas en este mundo, las que entienden binario y las que no. Adivina qué zona es."
            "computacion" -> currentHint =
                "Dicen que en esta zona encuentras los chismes hasta en papel. Te suena muy difícil? hablo de revistas."
            "hemeroteca" -> currentHint =
                "Baja baja baja hasta el fondo sin parar. Lo sabrás cuando veas estudiantes trabajar."
            "sotano" -> currentHint =
                "Dicen que hay un tesoro oculto por esta zona ¿Podrás encontrarlo?"
            "tesoro" -> currentHint =
                ""
            else -> { currentHint =
                "La pista no fue reconocida..."
            }
        }
        findViewById<TextView>(R.id.current_hint_dialog).text = currentHint;
    }

    /**
     * Inicializa la app. Muestra los datos necesarios para la búsqueda e inicializa el lector QR
     */
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
        val persistence = DataPersistence(this)
        pointsCounter = Integer.parseInt(persistence.read(persistence.SCORE, "0"))
        findViewById<TextView>(R.id.points).text = ""+pointsCounter
        barcodeView = findViewById(R.id.vista_escaner_bv)
        var formatoQr = Arrays.asList(BarcodeFormat.QR_CODE)
        barcodeView.setDecoderFactory(DefaultDecoderFactory(formatoQr))
        barcodeView.decodeContinuous(eventoEscaneo)
    }

    /**
     * Guarda los datos en caché para mostrar cuando la actividad se encuentre visible de nuevo.
     */
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(POINTS_COUNTER_KEY, pointsCounter)
        outState.putString(CURRENT_HINT_KEY, currentHint)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    /**
     * Reestablece los valores guardados en caché y los muestra en la actividad
      */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        pointsCounter = savedInstanceState.getInt(POINTS_COUNTER_KEY)
        currentHint = savedInstanceState.getString(CURRENT_HINT_KEY).toString()
        Toast.makeText(
            this,
            ""+pointsCounter,
            Toast.LENGTH_SHORT
        ).show()
        findViewById<TextView>(R.id.points).setText(""+pointsCounter)
        findViewById<TextView>(R.id.current_hint_dialog).text = currentHint;
    }

    /**
     * Reactiva la vista de escaneo QR cuando la actividad es reanudada.
      */
    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }
    
    /**
     * Desactiva la vista de escaneo QR cuando la actividad es pausada.
      */
    override fun onPause() {
        barcodeView.pause()
        super.onPause()
    }

}
