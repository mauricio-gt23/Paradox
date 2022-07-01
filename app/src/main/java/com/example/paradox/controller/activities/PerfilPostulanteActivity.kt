package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.paradox.R
import com.example.paradox.databinding.ActivityPerfilPostulanteBinding
import com.example.paradox.databinding.FragmentAddCompanyBinding
import com.example.paradox.models.*
import com.example.paradox.network.PostulantService
import com.example.paradox.network.RegisterService
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

class PerfilPostulanteActivity : AppCompatActivity() {
    private var studyId : Int = 0
    private var skillId : Int = 0
    private var languageId : Int = 0
    private lateinit var languages : ArrayList<Language>
    private lateinit var studies : ArrayList<Study>
    private lateinit var skills : ArrayList<Skill>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_postulante)
        getData()
        val studyAdapter = ArrayAdapter(this, R.layout.prototype_study, studies)
        val skillAdapter = ArrayAdapter(this, R.layout.prototype_skill, skills)
        val languageAdapter = ArrayAdapter(this, R.layout.prototype_language, languages)


        val atvStudy = findViewById<AutoCompleteTextView>(R.id.atvStudy)
        atvStudy.setAdapter(studyAdapter)
        atvStudy.setOnItemClickListener { _, _, position, _ ->
            val selectedStudy = studyAdapter.getItem(position) as Study
            studyId = selectedStudy.id
            Log.d("idbri", studyId.toString())
        }

        val atvSkill = findViewById<AutoCompleteTextView>(R.id.atvSkill)
        atvSkill.setAdapter(skillAdapter)
        atvSkill.setOnItemClickListener { _, _, position, _ ->
            val selectedSkill = skillAdapter.getItem(position) as Skill
            languageId = selectedSkill.id
        }

        val atvLanguage = findViewById<AutoCompleteTextView>(R.id.atvLanguage)
        atvLanguage.setAdapter(languageAdapter)
        atvLanguage.setOnItemClickListener { _, _, position, _ ->
            val selectedLanguage = languageAdapter.getItem(position) as Language
            skillId = selectedLanguage.id
        }

        val crearPerfilPostulante = findViewById<Button>(R.id.btCrearPost)


        crearPerfilPostulante.setOnClickListener {
            addPostulantPerfil()

        }
    }

    private fun addPostulantPerfil() {
        val txOcupation = findViewById<EditText>(R.id.txtOcupationPerfil)
        val txDescription = findViewById<EditText>(R.id.txtDescriptionPerfil)

        if(txOcupation.text.isEmpty()){
            Toast.makeText(this, "Falta la ocupacion", Toast.LENGTH_SHORT).show()

        }
        else if(txDescription.text.isEmpty()){
            Toast.makeText(this, "Falta la descripcion", Toast.LENGTH_SHORT).show()

        }
        else{
            val ocupationPerfil = txOcupation.text.toString()
            val descriptionPerfil = txDescription.text.toString()
            val requestPerfil = RequestPostulantPerfilCaro(ocupationPerfil,"GA",descriptionPerfil

            )
            val sharedPreferences = SharedPreferences(this@PerfilPostulanteActivity)

            if (sharedPreferences != null) {
                if(sharedPreferences.getValues("registerId") != null) {
                 var   idd = sharedPreferences.getValues("registerId")!!.toLong()

                    val request = RegisterService.registerInstance.createPostulantProfile(idd,requestPerfil)

                    request.enqueue(object: Callback<ResponsePostulantPerfilCaro> {
                        override fun onFailure(call: Call<ResponsePostulantPerfilCaro>, t: Throwable) {
                            Log.d("44","Error in Adding Postualnt")
                        }

                        override fun onResponse(call: Call<ResponsePostulantPerfilCaro>, response: Response<ResponsePostulantPerfilCaro?>) {

                            if (response.code() === 404) {

                                val gson = GsonBuilder().create()
                                try {

                                    val pojo = gson.fromJson(
                                        response.errorBody()!!.string(),
                                        Errores::class.java)

                                    Log.e("ERROR_CHECK","here else is the error${pojo.message}")

                                    Toast.makeText(this@PerfilPostulanteActivity, pojo.message, Toast.LENGTH_LONG).show()

                                } catch (e: IOException) {
                                    // handle failure at e
                                }}
                            if(response.isSuccessful){


                                Toast.makeText(this@PerfilPostulanteActivity, "Successfully created", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@PerfilPostulanteActivity, MainActivity::class.java)
                                startActivity(intent)


                            }


                            else  {
                                Log.d("AddPostulantActivity", response.errorBody()!!.toString())

                            }

                        }
                    })
                }
                }


            }


        }

    private fun getData() {
        studies = ArrayList<Study>()
        languages = ArrayList<Language>()
        skills = ArrayList<Skill>()

        val request = PostulantService.postulantInstance.getAllLanguages()
        val request1 = PostulantService.postulantInstance.getAllStudies()
        val request2 = PostulantService.postulantInstance.getAllSkills()

        request.enqueue(object : Callback<Languages> {
            override fun onResponse(call: Call<Languages>, response: Response<Languages>) {
                if (response.isSuccessful){
                    val content = response.body()
                    if (content != null) {
                        languages.apply {
                            addAll(content.languages)
                        }
                        Log.d("Brigitte", languages.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Languages>, t: Throwable) {
                Toast.makeText(this@PerfilPostulanteActivity, "Languages could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })

        request1.enqueue(object : Callback<Studies> {
            override fun onResponse(call: Call<Studies>, response: Response<Studies>) {
                if (response.isSuccessful){
                    val content = response.body()
                    if (content != null) {
                        studies.apply {
                            addAll(content.studies)
                        }
                        Log.d("Brigitte", studies.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Studies>, t: Throwable) {
                Toast.makeText(this@PerfilPostulanteActivity, "Studies could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })

        request2.enqueue(object : Callback<Skills> {
            override fun onResponse(call: Call<Skills>, response: Response<Skills>) {
                if (response.isSuccessful){
                    val content = response.body()
                    if (content != null) {
                        skills.apply {
                            addAll(content.skills)
                        }
                        Log.d("Brigitte", skills.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Skills>, t: Throwable) {
                Toast.makeText(this@PerfilPostulanteActivity, "Skills could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })
    }



}