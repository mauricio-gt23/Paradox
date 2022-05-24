package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import com.example.paradox.R
import com.example.paradox.models.User
import com.example.paradox.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                loadUser()
                Toast.makeText(this, "Bienvenido ${name}", Toast.LENGTH_SHORT).show()

            } else {
                tvErrorLogin.isInvisible = false
            }
        }

        btRegister.setOnClickListener {

            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


        //val languages = resources.getStringArray(R.array.languages)

    }

    private fun loadUser() {
        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val name = etUser.text.toString()
        val password = etPassword.text.toString()
        val request = UserService.userInstance.getAuth(name, password)
        request.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("ga", "Error in Fetching Company")
            }
            override fun onResponse(call: Call<User>, response: Response<User>) {
            }
        })
    }
}