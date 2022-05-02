package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btRegisterEmployeerActivity = findViewById<Button>(R.id.btRegisterEmployeer)
        val btRegisterPostulantActivity = findViewById<Button>(R.id.btRegisterPostulant)

        btRegisterEmployeerActivity.setOnClickListener {

            val intent = Intent(this, RegisterEmployeerActivity::class.java)
            startActivity(intent)
        }

        btRegisterPostulantActivity.setOnClickListener {

            val intent = Intent(this, RegisterPostulantActivity::class.java)
            startActivity(intent)
        }
    }
}