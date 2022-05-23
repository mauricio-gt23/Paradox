package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.paradox.R
import com.example.paradox.models.Postulant
import com.example.paradox.network.PostulantService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditProfilePostulant : AppCompatActivity() {

    val etNameProfProfile = findViewById<EditText>(R.id.etNameProfProfile)
    val etLastNameProfProfile = findViewById<EditText>(R.id.etLastNameProfProfile)
    val etPhoneProfProfile = findViewById<EditText>(R.id.etPhoneProfProfile)
    val etCivilStatusProfile = findViewById<EditText>(R.id.etCivilStatusProfile)
    val etPhoneProfProfileEdit = findViewById<EditText>(R.id.etPhoneProfProfileEdit)
    val etProfEmail = findViewById<EditText>(R.id.etProfEmail)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_postulant)
        if (intent.extras != null) {
            loadData()
        }
        val buttonSaveEditProfile = findViewById<Button>(R.id.btSaveEdit)
        buttonSaveEditProfile.setOnClickListener {
            saveEditedPostulant()
        }
    }

    private fun saveEditedPostulant() {
        val postulant: Postulant = intent.getParcelableExtra("Postulant")!!
        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postulantService: PostulantService
        postulantService = retrofit.create(PostulantService::class.java)

        val postulantEdited = Postulant(postulant.id, etNameProfProfile.text.toString(),
            etLastNameProfProfile.text.toString(), etProfEmail.text.toString(),
            9952, postulant.password,etPhoneProfProfile.text.toString(),
            etCivilStatusProfile.text.toString())
        )
        val request = postulantService.editPostulant(postulant.id, postulantEdited)
    }

    private fun loadData() {

        val postulant: Postulant = intent.getParcelableExtra("Postulant")!!
        etNameProfProfile.setText(postulant.firstName)
        etLastNameProfProfile.setText(postulant.lastName)
        etPhoneProfProfile.setText(postulant.document)
        etCivilStatusProfile.setText(postulant.civilStatus)
        etPhoneProfProfileEdit.setText(postulant.number.toString())
        etProfEmail.setText(postulant.email)

    }
}