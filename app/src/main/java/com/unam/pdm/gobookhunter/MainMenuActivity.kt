package com.unam.pdm.gobookhunter

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


class MainMenuActivity: AppCompatActivity()  {
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main_menu);
        val recorrido= findViewById<ImageButton>(R.id.imageButton)

        recorrido.setOnClickListener{
            barcodeLauncher.launch(ScanOptions())
        }

    }

    private val barcodeLauncher: ActivityResultLauncher<ScanOptions> =
        registerForActivityResult<ScanOptions, ScanIntentResult>(ScanContract(),
            ActivityResultCallback { result: ScanIntentResult ->
                if (result.contents == null) {
                    Toast.makeText(this@MainMenuActivity, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        this@MainMenuActivity,
                        "Scanned: " + result.contents,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
}