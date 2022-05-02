package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isInvisible

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btLogin = findViewById<Button>(R.id.btLogin)
        val btRegister = findViewById<Button>(R.id.btCreateAccount)



        btLogin.setOnClickListener {

            val tvErrorLogin = findViewById<TextView>(R.id.tvErrorLogin)
            val name = etUser.text.toString()
            val password = etPassword.text.toString()
            if (name.isNotBlank() && password.isNotBlank()) {

                Toast.makeText(this, "Bienvenido(aqui poner la vista del home)", Toast.LENGTH_SHORT).show()


            } else {
                tvErrorLogin.isInvisible = false
            }


        }


        btRegister.setOnClickListener {

            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


    }

}