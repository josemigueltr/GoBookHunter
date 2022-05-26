package com.unam.pdm.gobookhunter

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unam.pdm.gobookhunter.databinding.ActivityMainBinding

class MainMenuActivity: AppCompatActivity()  {
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main_menu);
        val recorrido= findViewById<ImageButton>(R.id.imageButton)

        recorrido.setOnClickListener{

            Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show()
        }



    }
}