package com.example.paradox.controller.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.paradox.R
import com.example.paradox.models.Errores
import com.example.paradox.models.RequestEmployeer
import com.example.paradox.models.ResponseEmployeer
import com.example.paradox.network.RegisterService
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class RegisterEmployeerActivity : AppCompatActivity() {
    val errores = mutableListOf<String>()
    val success = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_employeer)

        val btRegister = findViewById<Button>(R.id.btRegistrarseEmp)
        val btncheckBox = findViewById<CheckBox>(R.id.btncheckBox)


        btRegister.setOnClickListener {


         if(btncheckBox.isChecked()){

             addCompany()
             if(errores.size>0){
                 Toast.makeText(this, errores[0], LENGTH_SHORT).show()
                 errores.removeAt(0)
             }
             if(success.size>0) {
                 val intent = Intent(this, MainActivity::class.java)
                 startActivity(intent)
                 Toast.makeText(this, "Se registro correctamente", LENGTH_SHORT).show()
             }
         }
             else{
             Toast.makeText(this, "No olvides aceptar los términos y condiciones", LENGTH_SHORT).show()
         }








        }

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
                 posicionEmp
             )


             val request = RegisterService.registerInstance.createEmployeer(requestEmployeer)


             request.enqueue(object: Callback<ResponseEmployeer> {
                 override fun onFailure(call: Call<ResponseEmployeer>, t: Throwable) {
                     Log.d("ga","Error in Adding Employeer")
                 }

                 override fun onResponse(call: Call<ResponseEmployeer>, response: Response<ResponseEmployeer?>) {
                     val employeerAdded = response.body()
                     if(response.isSuccessful){
                         if (response.code() === 404) {

                             val gson = GsonBuilder().create()
                             try {

                                 val pojo = gson.fromJson(
                                     response.errorBody()!!.string(),
                                     Errores::class.java)

                                 Log.e("ERROR_CHECK","here else is the error${pojo.message}")

                                 errores.add(pojo.message)

                             } catch (e: IOException) {
                                 // handle failure at e
                             }}
                         else{
                             success.add("Success")
                         }

                     }


                     else  {
                         Log.d("AddEmployeerActivity", response.errorBody()!!.toString())

                     }

                 }
             })
         }



    }
}