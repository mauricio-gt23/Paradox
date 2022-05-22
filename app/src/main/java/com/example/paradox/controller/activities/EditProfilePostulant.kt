package com.example.paradox.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.paradox.R
import com.example.paradox.models.Postulant

class EditProfilePostulant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_postulant)
        if (intent.extras != null) {
            loadData()
        }
    }

    private fun loadData() {
        val etNameProfProfile = findViewById<EditText>(R.id.etNameProfProfile)
        val postulant: Postulant = intent.getParcelableExtra("postulant")!!
        etNameProfProfile.setText(postulant.firstName)
    }
}