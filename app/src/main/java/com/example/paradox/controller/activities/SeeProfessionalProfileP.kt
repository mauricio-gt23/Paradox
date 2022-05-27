package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.paradox.R
import com.example.paradox.models.ProfProfile
import com.example.paradox.network.PostulantService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SeeProfessionalProfileP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_professional_profile_p)
        loadProfessionalProfile()

        val buttonEditProfessional = findViewById<FloatingActionButton>(R.id.btGoToEditProfessional)
        buttonEditProfessional.setOnClickListener {
            val intent = Intent(this, EditProfessionalProfileP::class.java)
            startActivity(intent)
        }

    }

    fun loadProfessionalProfile() {
        val tvOcupacionTextProfProfile = findViewById<TextView>(R.id.tvOcupacionTextProfProfile)
        val tvDescriptionTextProfProfile = findViewById<TextView>(R.id.tvDescriptionTextProfProfile)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postulantService: PostulantService = retrofit.create(PostulantService::class.java)

        val request = postulantService.getProfileByIdAndPostulantId(4, 1)

        request.enqueue(object : Callback<ProfProfile> {
            override fun onResponse(call: Call<ProfProfile>, response: Response<ProfProfile>) {
                if (response.isSuccessful){
                    tvOcupacionTextProfProfile.text = response.body()!!.ocupation
                    tvDescriptionTextProfProfile.text = response.body()!!.description

                }
            }

            override fun onFailure(call: Call<ProfProfile>, t: Throwable) {
                 Toast.makeText(this@SeeProfessionalProfileP, "Data could not be retrieved", Toast.LENGTH_LONG).show()
            }

        })
    }
}