package com.unam.pdm.gobookhunter

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.unam.pdm.gobookhunter.databinding.ActivityMainBinding
import com.unam.pdm.gobookhunter.utilities.QrScanner


class MainMenuActivity: AppCompatActivity()  {
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main_menu);
        val recorrido= findViewById<ImageButton>(R.id.imageButton)

        val qrScanner = QrScanner.getInstance(this)

        recorrido.setOnClickListener{
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            options.setPrompt("Scan a barcode")
            options.setBeepEnabled(false)
            options.setBarcodeImageEnabled(true)
            qrScanner.scann(options)
        }

        findViewById<ImageButton>(R.id.btn_rewards).setOnClickListener {
            val intent = Intent(this, RewardsActivity::class.java)
            startActivity(intent)
        }
    }
}