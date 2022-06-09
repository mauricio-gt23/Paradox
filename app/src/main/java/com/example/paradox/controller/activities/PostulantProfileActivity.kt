package com.example.paradox.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.paradox.R
import com.example.paradox.models.Postul
import com.google.gson.Gson

class PostulantProfileActivity : AppCompatActivity() {

    lateinit var postul: Postul

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postulant_profile)

        loadPostulantProfile()
    }

    private fun loadPostulantProfile() {
        val gson = Gson()
        val stringObj = intent.getStringExtra("postul")

        postul = gson.fromJson(stringObj, Postul::class.java) ?: Postul(null, "", "", "", "", null)

        if (postul.id != null) {
            findViewById<TextView>(R.id.tvNamePos).text = postul.firstname
            findViewById<TextView>(R.id.tvLastPos).text = postul.lastname
            findViewById<TextView>(R.id.tvDocument).text = postul.document
            findViewById<TextView>(R.id.tvNum).text = postul.number.toString()
            findViewById<TextView>(R.id.tvGmail).text = postul.email
        }
    }
}