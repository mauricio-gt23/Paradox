package com.example.paradox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText



class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val btLogin = findViewById<Button>(R.id.btLogin)

        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)



        btLogin.setOnClickListener {


            val name = etUser.text.toString()
            val password = etPassword.text.toString()







        }


    }



}