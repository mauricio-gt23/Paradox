package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.paradox.R
import com.example.paradox.models.Postulant
import com.example.paradox.network.PostulantService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SeeProfilePostulant : AppCompatActivity() {

    var postulant: Postulant = TODO()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_profile_postulant)
        loadPostulant()

        val buttonEdit = findViewById<FloatingActionButton>(R.id.fabEditProfile)
        buttonEdit.setOnClickListener {
            val intent = Intent(this, EditProfilePostulant::class.java)
            intent.putExtra("Postulant", this.postulant)
            startActivity(intent)
        }
    }

    public fun loadPostulant() {
        val tvNameShow = findViewById<TextView>(R.id.tvNameShow)
        val tvLastNameShow = findViewById<TextView>(R.id.tvLastNameShow)
        val tvIdDocShow = findViewById<TextView>(R.id.tvIdDocShow)
        val tvCivilStatusShow = findViewById<TextView>(R.id.tvCivilStatusShow)
        val tvPhoneShow = findViewById<TextView>(R.id.tvPhoneShow)
        val tvEmailShow = findViewById<TextView>(R.id.tvEmailShow)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postulantService: PostulantService
        postulantService = retrofit.create(PostulantService::class.java)

        val request = postulantService.getPostulantById(4)

        request.enqueue(object : Callback<Postulant> {
            override fun onResponse(call: Call<Postulant>, response: Response<Postulant>) {
                if (response.isSuccessful){
                    val postulantRetrieved = response.body()
                    tvNameShow.text = postulantRetrieved!!.firstName
                    tvLastNameShow.text = postulantRetrieved.lastName
                    tvIdDocShow.text = postulantRetrieved.document
                    tvCivilStatusShow.text = postulantRetrieved.civilStatus
                    tvPhoneShow.text = postulantRetrieved.number.toString()
                    tvEmailShow.text = postulantRetrieved.email
                    postulant = Postulant(postulantRetrieved.id, postulantRetrieved.firstName, postulantRetrieved.lastName,
                    postulantRetrieved.email, postulantRetrieved.number, postulantRetrieved.password, postulantRetrieved.document,
                    postulantRetrieved.civilStatus)
                }
            }

            override fun onFailure(call: Call<Postulant>, t: Throwable) {
            }

        })
    }

}