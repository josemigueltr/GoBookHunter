package com.unam.pdm.gobookhunter

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


/**
 * Menú prinipal. Muestra botones que inician las demás actividades de la app.
 */
class MainMenuActivity: AppCompatActivity()  {

    // Constante para solicitar los permisos de cámara
    val CODIGO_SOLICITUD_CAMARA = 3

    /**
     * Inicializa la app. Agrega acciones a los botones para completar la navegación
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val recorrido = findViewById<ImageButton>(R.id.imageButton)

        recorrido.setOnClickListener{
            val intent = Intent(this, QrHuntActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btn_rewards).setOnClickListener {
            val intent = Intent(this, RewardsActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Pide permisos para usar la cámara del teléfono al momento de mostrar la actividad,
     * en caso de que aún no tengamos permisos.
     */
    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), CODIGO_SOLICITUD_CAMARA);
        }
    }

    /**
     * Recibe el resultado de la solicitud de permisos. Si el usuario otorga permisos para
     * usar la camara, muestra un mensaje de confirmación. En caso contrario, muestra una
     * advertencia.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODIGO_SOLICITUD_CAMARA) {
            // TODO Estos textos deberían ser recursos de cadena
            // TODO Los textos también podrían ser más descriptivos
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this, "Ahora puede escanear códigos QR",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this, "Necesita otorgar el permiso para usar la " +
                            "cámara para poder usar la aplicación", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}