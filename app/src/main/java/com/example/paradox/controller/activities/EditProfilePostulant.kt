package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.paradox.NavigationPostulantActivity
import com.example.paradox.R
import com.example.paradox.models.PostulantBri
import com.example.paradox.network.PostulantService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfilePostulant : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_postulant)
        if (intent.extras != null) {
            loadData()
        }
        val buttonSaveEditProfile = findViewById<Button>(R.id.btSaveEdit)
        buttonSaveEditProfile.setOnClickListener {
            Log.d("bri", "button pressed");
            saveEditedPostulant()
        }
    }

    private fun saveEditedPostulant() {
        val postulantBri: PostulantBri = intent.getParcelableExtra("Postulant")!!
        val etNameProfProfile = findViewById<EditText>(R.id.etNameProfProfile)
        val etLastNameProfProfile = findViewById<EditText>(R.id.etLastNameProfProfile)
        val etPhoneProfProfile = findViewById<EditText>(R.id.etPhoneProfProfile)
        val etCivilStatusProfile = findViewById<EditText>(R.id.etCivilStatusProfile)
        val etPhoneProfProfileEdit = findViewById<EditText>(R.id.etPhoneProfProfileEdit)
        val etProfEmail = findViewById<EditText>(R.id.etProfEmail)

        val postulantBriEdited = PostulantBri(postulantBri.id, etNameProfProfile.text.toString(),
            etLastNameProfProfile.text.toString(), etProfEmail.text.toString(),
            etPhoneProfProfileEdit.text.toString().toInt(), postulantBri.password,etPhoneProfProfile.text.toString(),
            etCivilStatusProfile.text.toString(), postulantBri.link, postulantBri.other)
        val request = PostulantService.postulantInstance.editPostulant(postulantBri.id, postulantBriEdited)
        request.enqueue(object : Callback<PostulantBri> {
            override fun onResponse(call: Call<PostulantBri>, response: Response<PostulantBri>) {
                if (response.isSuccessful){
                    val editedPostulant = response.body()
                    if (editedPostulant != null) {
                        Toast.makeText(this@EditProfilePostulant, "Successfully updated", Toast.LENGTH_LONG).show()
                        Handler().postDelayed({
                            val intent = Intent(this@EditProfilePostulant, NavigationPostulantActivity::class.java)
                            startActivity(intent)
                        }, 1700)
                    }

                }
            }

            override fun onFailure(call: Call<PostulantBri>, t: Throwable) {
                Toast.makeText(this@EditProfilePostulant, "There was a problem updating the info ", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun loadData() {

        val postulantBri: PostulantBri = intent.getParcelableExtra("Postulant")!!
        val etNameProfProfile = findViewById<EditText>(R.id.etNameProfProfile)
        val etLastNameProfProfile = findViewById<EditText>(R.id.etLastNameProfProfile)
        val etPhoneProfProfile = findViewById<EditText>(R.id.etPhoneProfProfile)
        val etCivilStatusProfile = findViewById<EditText>(R.id.etCivilStatusProfile)
        val etPhoneProfProfileEdit = findViewById<EditText>(R.id.etPhoneProfProfileEdit)
        val etProfEmail = findViewById<EditText>(R.id.etProfEmail)
        val ivProfilePhotoEdir = findViewById<ImageView>(R.id.ivProfilePhotoEdir)

        etNameProfProfile.setText(postulantBri.firstName)
        etLastNameProfProfile.setText(postulantBri.lastName)
        etPhoneProfProfile.setText(postulantBri.document)
        etCivilStatusProfile.setText(postulantBri.civilStatus)
        etPhoneProfProfileEdit.setText(postulantBri.number.toString())
        etProfEmail.setText(postulantBri.email)
        Glide.with(this@EditProfilePostulant).load(postulantBri.link).into(ivProfilePhotoEdir)

    }
}

