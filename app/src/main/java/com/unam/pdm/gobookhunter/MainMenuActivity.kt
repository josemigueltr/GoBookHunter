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
import com.unam.pdm.gobookhunter.databinding.ActivityMainBinding


class MainMenuActivity: AppCompatActivity()  {
    private  lateinit var binding: ActivityMainBinding

    val CODIGO_SOLICITUD_CAMARA = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main_menu);

        val recorrido = findViewById<ImageButton>(R.id.imageButton)

        recorrido.setOnClickListener{
            var intent = Intent(this, QrHuntActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btn_rewards).setOnClickListener {
            val intent = Intent(this, RewardsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), CODIGO_SOLICITUD_CAMARA);
        }
    }

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