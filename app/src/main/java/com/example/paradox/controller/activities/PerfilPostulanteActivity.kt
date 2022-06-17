package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.paradox.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_postulante)


        val crearPerfilPostulante = findViewById<Button>(R.id.btCrearPost)


        crearPerfilPostulante.setOnClickListener {
            addPostulantPerfil()


        }


        val spinner_grado_estudios: Spinner = findViewById(R.id.spGradoDeEstudios)
        ArrayAdapter.createFromResource(
            this,
            R.array.grado_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_grado_estudios.adapter = adapter
        }

        val spinner_habilidades: Spinner = findViewById(R.id.spHabilidades)
        ArrayAdapter.createFromResource(
            this,
            R.array.habilidades_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_habilidades.adapter = adapter
        }
        val spinner_Idiomas: Spinner = findViewById(R.id.spIdiomas)
        ArrayAdapter.createFromResource(
            this,
            R.array.idiomas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_Idiomas.adapter = adapter
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