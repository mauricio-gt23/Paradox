package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.paradox.R
import com.example.paradox.models.Errores
import com.example.paradox.models.RequestEmployeer
import com.example.paradox.models.ResponseEmployeer
import com.example.paradox.network.RegisterService
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.HashMap

class RegisterPostulantActivity : AppCompatActivity() {
    private val File = 1
    private val database = Firebase.database
    val myRef = database.getReference("user")
    private var prueba = "prueba"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_postulant)
        val etPerfilPostulante = findViewById<Button>(R.id.btnContinuarPostulante)

        val uploadImageView = findViewById<ImageView>(R.id.uploadImageViewPost)
        uploadImageView.setOnClickListener {
            fileUpload()
        }


        etPerfilPostulante.setOnClickListener {

            val intent = Intent(this, PerfilPostulanteActivity::class.java)
            startActivity(intent)
            addPostulant()
        }
    }

    fun fileUpload() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, File)
    }
    private fun addPostulant() {

        val txCreateNamePost = findViewById<EditText>(R.id.txtCreateNamePost)
        val txCreateApellidoPost = findViewById<EditText>(R.id.txtCreateApellidoPost)
        val txtEmailPost = findViewById<EditText>(R.id.txtCreateEmailPost)
        val txtPhonePost = findViewById<EditText>(R.id.txtCreatePhonePost)
        val txtcontraPost = findViewById<EditText>(R.id.txtCreateContraPost)
        val txDocumentoIdentidadPost = findViewById<EditText>(R.id.txtCreateDocumentoIdentidadPost)

        val txtCivilStatusPost = findViewById<EditText>(R.id.txtCreateEstadoCivilPost)



        if(txCreateNamePost.text.isEmpty()){
            Toast.makeText(this, "Falta el Nombre", Toast.LENGTH_SHORT).show()

        }
        else if(txCreateApellidoPost.text.isEmpty()){
            Toast.makeText(this, "Falta el Apellido", Toast.LENGTH_SHORT).show()

        }
        else if(txtEmailPost.text.isEmpty()){
            Toast.makeText(this, "Falta el Email", Toast.LENGTH_SHORT).show()

        }
        else if(txtPhonePost.text.isEmpty()){
            Toast.makeText(this, "Falta el Teléfono", Toast.LENGTH_SHORT).show()

        }
        else if(txtcontraPost.text.isEmpty()){
            Toast.makeText(this, "Falta la Contraseña", Toast.LENGTH_SHORT).show()

        }
        else if(txDocumentoIdentidadPost.text.isEmpty()){
            Toast.makeText(this, "Falta el Documento de Identidad", Toast.LENGTH_SHORT).show()

        }
        else if(txtCivilStatusPost.text.isEmpty()){
            Toast.makeText(this, "Falta el estado civil ", Toast.LENGTH_SHORT).show()

        }
        else{
            val namePost = txCreateNamePost.text.toString()
            val apellidoPost = txCreateApellidoPost.text.toString()
            val identidadPost = txDocumentoIdentidadPost.text.toString()
            val phonePost = txtPhonePost.text.toString().toLong()
            val emailPost = txtEmailPost.text.toString()
            val contraPost = txtcontraPost.text.toString()
            val requestEmployeer = RequestEmployeer(namePost,apellidoPost,identidadPost, phonePost,emailPost,contraPost,
                prueba

            )


            val request = RegisterService.registerInstance.createEmployeer(requestEmployeer)


            request.enqueue(object: Callback<ResponseEmployeer> {
                override fun onFailure(call: Call<ResponseEmployeer>, t: Throwable) {
                    Log.d("44","Error in Adding Postualnt")
                }

                override fun onResponse(call: Call<ResponseEmployeer>, response: Response<ResponseEmployeer?>) {

                    if (response.code() === 404) {

                        val gson = GsonBuilder().create()
                        try {

                            val pojo = gson.fromJson(
                                response.errorBody()!!.string(),
                                Errores::class.java)

                            Log.e("ERROR_CHECK","here else is the error${pojo.message}")

                            Toast.makeText(this@RegisterPostulantActivity, pojo.message, Toast.LENGTH_LONG).show()

                        } catch (e: IOException) {
                            // handle failure at e
                        }}
                    if(response.isSuccessful){


                        Toast.makeText(this@RegisterPostulantActivity, "Successfully created", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegisterPostulantActivity, MainActivity::class.java)
                        startActivity(intent)


                    }


                    else  {
                        Log.d("AddPostulantActivity", response.errorBody()!!.toString())

                    }

                }
            })
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == File) {
            if (resultCode == RESULT_OK) {
                val FileUri = data!!.data
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("User")
                val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.getDownloadUrl().addOnSuccessListener { uri ->
                        val hashMap =
                            HashMap<String, String>()
                        hashMap["link"] = java.lang.String.valueOf(uri)
                        myRef.setValue(hashMap)
                        prueba = uri.toString()
                        if(uri.toString().isNotEmpty()){
                            Toast.makeText(this@RegisterPostulantActivity, "Se subio el archivo correctamente!", Toast.LENGTH_LONG).show()
                        }
                        Log.d("Mensaje", uri.toString())
                    }
                }
            }
        }
    }
}