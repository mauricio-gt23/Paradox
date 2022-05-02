package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterPostulantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_postulant)
        val etPerfilPostulante = findViewById<Button>(R.id.btnContinuarPostulante)


        etPerfilPostulante.setOnClickListener {

            val intent = Intent(this, PerfilPostulanteActivity::class.java)
            startActivity(intent)
        }
    }
}