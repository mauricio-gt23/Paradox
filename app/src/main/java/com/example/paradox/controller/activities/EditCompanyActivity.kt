package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.paradox.R
import com.example.paradox.models.Company
import com.example.paradox.network.CompaniesService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCompanyActivity : AppCompatActivity() {
    lateinit var company: Company

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_company)

        loadCompany()

        val btBack2 = findViewById<ImageButton>(R.id.btBack2)
        val btSaveEditedCompany = findViewById<Button>(R.id.btSaveEditedCompany)

        btBack2.setOnClickListener{
            val intent = Intent(this, ViewCompanyEmployerActivity::class.java)
            startActivity(intent)
        }

        btSaveEditedCompany.setOnClickListener{
            editCompany()
            val intent = Intent(this, ViewCompanyEmployerActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loadCompany() {
        val etEditName = findViewById<EditText>(R.id.etEditName)
        val etEditRuc = findViewById<EditText>(R.id.etEditRuc)
        val etEditAddress = findViewById<EditText>(R.id.etEditAddress)
        val etEditDescription = findViewById<EditText>(R.id.etEditDescription)
        val etEditLogo = findViewById<EditText>(R.id.etEditLogo)

        val gson = Gson()
        val stringObj = intent.getStringExtra("company")
        company = gson.fromJson(stringObj, Company::class.java) ?: Company(0, "", "", "",0,"", 0, 0)

        if (company.id != 0){
            etEditName.setText(company.name)
            etEditRuc.setText(company.ruc)
            etEditAddress.setText(company.direccion)
            etEditDescription.setText(company.description)
            etEditLogo.setText(company.logo)
        }
    }

    private fun editCompany(){
        val request = CompaniesService.companiesInstance.editCompany(company.id, company)
        request.enqueue(object: Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("EditCompanyActivity","Error in Editing Company")
            }

            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val editedCompany = response.body()
                if (editedCompany != null) {
                    Log.d("EditCompanyActivity", editedCompany.toString())
                }
            }
        })
    }
}