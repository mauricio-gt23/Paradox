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
    private lateinit var studies : ArrayList<Study>
    private lateinit var skills: ArrayList<Skill>
    private lateinit var languages : ArrayList<Language>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_postulante)

        val studyAdapter = ArrayAdapter(this, R.layout.prototype_study, studies)
        val skillAdapter = ArrayAdapter(this, R.layout.prototype_study, skills)
        val languageAdapter = ArrayAdapter(this, R.layout.prototype_study, languages)

        val atvStudy = findViewById<AutoCompleteTextView>(R.id.atvStudy)
        atvStudy.setOnItemClickListener { _, _, position, _ ->
            val selectedStudy = studyAdapter.getItem(position) as Study
            studyId = selectedStudy.id
        }

        val atvSkill = findViewById<AutoCompleteTextView>(R.id.atvSkill)
        atvSkill.setOnItemClickListener { _, _, position, _ ->
            val selectedSkill = skillAdapter.getItem(position) as Skill
            languageId = selectedSkill.id
        }

        val atvLanguage = findViewById<AutoCompleteTextView>(R.id.atvLanguage)
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





}