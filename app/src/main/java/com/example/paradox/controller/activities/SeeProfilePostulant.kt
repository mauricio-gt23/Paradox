package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paradox.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SeeProfilePostulant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_profile_postulant)

        val buttonEdit = findViewById<FloatingActionButton>(R.id.fabEditProfile)
        buttonEdit.setOnClickListener {
            val intent = Intent(this, EditProfilePostulant::class.java)
            startActivity(intent)
        }
    }

}