package com.example.paradox.controller.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.paradox.R
import com.example.paradox.models.Errores
import com.example.paradox.models.RequestEmployeer
import com.example.paradox.models.ResponseEmployeer
import com.example.paradox.models.Sector
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


class RegisterEmployeerActivity : AppCompatActivity() {
    private val File = 1
    private val database = Firebase.database
    val myRef = database.getReference("user")
    private var prueba = "prueba"
    val errores = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_employeer)

        val btRegister = findViewById<Button>(R.id.btRegistrarseEmp)
        val btncheckBox = findViewById<CheckBox>(R.id.btncheckBox)
        val uploadImageView = findViewById<ImageView>(R.id.uploadImageView)
        uploadImageView.setOnClickListener {
            fileUpload()
        }

        btRegister.setOnClickListener {


            if (btncheckBox.isChecked) {

                addCompany()
                Log.d("Mensajess",prueba)
            } else {
                Toast.makeText(this, "No olvides aceptar los términos y condiciones", LENGTH_SHORT)
                    .show()


            }
        }

    }
    fun fileUpload() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, File)
    }

    private fun addCompany() {

        val txCreateNameEmployeer = findViewById<EditText>(R.id.txCreateNameEmp)
        val txCreateApellidoEmp = findViewById<EditText>(R.id.txCreateApellidoEmp)
        val txDocumentoIdentidadEmp = findViewById<EditText>(R.id.txDocumentoIdentidadEmp)
        val txtPhoneEmp = findViewById<EditText>(R.id.txtPhoneEmp)
        val txtPosicionEmp = findViewById<EditText>(R.id.txtPosicionEmp)
        val txtEmailEmp = findViewById<EditText>(R.id.txtEmailEmp)
        val txtContraEmp = findViewById<EditText>(R.id.txtContraEmp)

         if(txCreateNameEmployeer.text.isEmpty()){
             Toast.makeText(this, "Falta el Nombre", Toast.LENGTH_SHORT).show()

         }
        else if(txCreateApellidoEmp.text.isEmpty()){
            Toast.makeText(this, "Falta el Apellido", Toast.LENGTH_SHORT).show()

        }
         else if(txDocumentoIdentidadEmp.text.isEmpty()){
            Toast.makeText(this, "Falta el Documento de identidad", Toast.LENGTH_SHORT).show()

        }
         else if(txtPhoneEmp.text.isEmpty()){
            Toast.makeText(this, "Falta el Teléfono", Toast.LENGTH_SHORT).show()

        }
         else if(txtPosicionEmp.text.isEmpty()){
            Toast.makeText(this, "Falta el Posición", Toast.LENGTH_SHORT).show()

        }
         else if(txtEmailEmp.text.isEmpty()){
            Toast.makeText(this, "Falta el Email", Toast.LENGTH_SHORT).show()

        }
         else if(txtContraEmp.text.isEmpty()){
            Toast.makeText(this, "Falta la Contraseña ", Toast.LENGTH_SHORT).show()

        }
        else{
             val nameEmp = txCreateNameEmployeer.text.toString()
             val apellidoEmp = txCreateApellidoEmp.text.toString()
             val identidadEmp = txDocumentoIdentidadEmp.text.toString()
             val phoneEmp = txtPhoneEmp.text.toString().toLong()
             val posicionEmp = txtPosicionEmp.text.toString()
             val emailEmp = txtEmailEmp.text.toString()
             val contraEmp = txtContraEmp.text.toString()
             val requestEmployeer = RequestEmployeer(nameEmp,apellidoEmp,emailEmp, phoneEmp,contraEmp,identidadEmp,
                 prueba
             )


             val request = RegisterService.registerInstance.createEmployeer(requestEmployeer)


             request.enqueue(object: Callback<ResponseEmployeer> {
                 override fun onFailure(call: Call<ResponseEmployeer>, t: Throwable) {
                     Log.d("ga","Error in Adding Employeer")
                 }

                 override fun onResponse(call: Call<ResponseEmployeer>, response: Response<ResponseEmployeer?>) {

                     if (response.code() === 404) {

                         val gson = GsonBuilder().create()
                         try {

                             val pojo = gson.fromJson(
                                 response.errorBody()!!.string(),
                                 Errores::class.java)

                             Log.e("ERROR_CHECK","here else is the error${pojo.message}")

                             Toast.makeText(this@RegisterEmployeerActivity, pojo.message, Toast.LENGTH_LONG).show()

                         } catch (e: IOException) {
                             // handle failure at e
                         }}
                     if(response.isSuccessful){


                             Toast.makeText(this@RegisterEmployeerActivity, "Successfully created", Toast.LENGTH_LONG).show()
                             val intent = Intent(this@RegisterEmployeerActivity, MainActivity::class.java)
                             startActivity(intent)


                     }


                     else  {
                         Log.d("AddEmployeerActivity", response.errorBody()!!.toString())

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
                        Log.d("Mensaje", uri.toString())
                    }
                }
            }
        }
    }
}