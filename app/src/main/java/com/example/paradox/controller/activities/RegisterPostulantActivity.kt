package com.example.paradox.controller.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.paradox.R
import com.example.paradox.models.*
import com.example.paradox.network.RegisterService
import com.example.paradox.network.UserService
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RegisterPostulantActivity : AppCompatActivity() {
    private val File = 1
    private val database = Firebase.database
    val myRef = database.getReference("user")
    private var prueba = "prueba"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_postulant)
        val etPerfilPostulante = findViewById<Button>(R.id.btnContinuarPostulante)
        val tvTerms = findViewById<TextView>(R.id.textView2)

        val uploadImageView = findViewById<ImageView>(R.id.uploadImageViewPost)
        uploadImageView.setOnClickListener {
            fileUpload()
        }


        etPerfilPostulante.setOnClickListener {
            addPostulant()


        }
        tvTerms.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.freeprivacypolicy.com/live/b00fac55-ba79-4c5f-a2bb-cada03733179"))
            startActivity(browserIntent)
        }
    }

    fun fileUpload() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        Log.d("intentbri", intent.toString())
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
        else if(txDocumentoIdentidadPost.text.isEmpty()){
            Toast.makeText(this, "Falta el Documento de Identidad", Toast.LENGTH_SHORT).show()

        }
        else if(txtCivilStatusPost.text.isEmpty()){
            Toast.makeText(this, "Falta el estado civil ", Toast.LENGTH_SHORT).show()

        }
        else if(txtPhonePost.text.isEmpty()){
            Toast.makeText(this, "Falta el Teléfono", Toast.LENGTH_SHORT).show()

        }
        else if(txtEmailPost.text.isEmpty()){
            Toast.makeText(this, "Falta el Email", Toast.LENGTH_SHORT).show()

        }

        else if(txtcontraPost.text.isEmpty()){
            Toast.makeText(this, "Falta la Contraseña", Toast.LENGTH_SHORT).show()

        }


        else{
            val namePost = txCreateNamePost.text.toString()
            val apellidoPost = txCreateApellidoPost.text.toString()
            val identidadPost = txDocumentoIdentidadPost.text.toString()
            val phonePost = txtPhonePost.text.toString().toLong()
            val emailPost = txtEmailPost.text.toString()
            val contraPost = txtcontraPost.text.toString()
            val contraCivilStatus = txtCivilStatusPost.text.toString()
            val requestPostulant = RequestPostulant(namePost,apellidoPost,emailPost,phonePost,contraPost,
                identidadPost, prueba,"ga", contraCivilStatus

            )


            val request = RegisterService.registerInstance.createPostulant(requestPostulant)


            request.enqueue(object: Callback<ResponsePostulant> {
                override fun onFailure(call: Call<ResponsePostulant>, t: Throwable) {
                    Log.d("44","Error in Adding Postualnt")
                }

                override fun onResponse(call: Call<ResponsePostulant>, response: Response<ResponsePostulant?>) {

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
                        val sharedPreferences = SharedPreferences(this@RegisterPostulantActivity)
                        val request = UserService.userInstance.getAuth(emailPost, contraPost)
                        request.enqueue(object : Callback<User> {
                            override fun onFailure(call: Call<User>, t: Throwable) {
                                Log.d("ga", "Err34r in Fetching Company")

                            }
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                if(response.isSuccessful){
                                    val user =  response.body()!!

                                    sharedPreferences.save("registerId", user.id.toString())


                                }

                            }
                        })

                        Toast.makeText(this@RegisterPostulantActivity, "Successfully created", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegisterPostulantActivity, PerfilPostulanteActivity::class.java)
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
                Log.d("firebase", FileUri.toString())
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, FileUri)
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