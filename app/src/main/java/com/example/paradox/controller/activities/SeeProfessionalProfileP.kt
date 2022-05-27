package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.paradox.R
import com.example.paradox.adapter.CompanyAdapter
import com.example.paradox.adapter.LanguageAdapter
import com.example.paradox.adapter.SkillAdapter
import com.example.paradox.adapter.StudyAdapter
import com.example.paradox.models.ProfProfile
import com.example.paradox.models.Skill
import com.example.paradox.models.Skills
import com.example.paradox.network.PostulantService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SeeProfessionalProfileP : AppCompatActivity() {

    lateinit var skillAdapter : SkillAdapter
    lateinit var languageAdapter: LanguageAdapter
    lateinit var studiesAdapter: StudyAdapter

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
        val request2 = postulantService.getSkillsByProfile(1)
        val request3 = postulantService.getStudiesByProfile(1)
        val request4 = postulantService.getLanguagesByProfile(1)

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

        request2.enqueue(object : Callback<Skills> {
            override fun onResponse(call: Call<Skills>, response: Response<Skills>) {
                if (response.isSuccessful){
                    
                }
            }

            override fun onFailure(call: Call<Skills>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}