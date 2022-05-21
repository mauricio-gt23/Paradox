package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paradox.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SeeProfessionalProfileP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_professional_profile_p)

        val buttonEditProfessional = findViewById<FloatingActionButton>(R.id.btGoToEditProfessional)
        buttonEditProfessional.setOnClickListener {
            val intent = Intent(this, EditProfessionalProfileP::class.java)
            startActivity(intent)
        }

    }
}