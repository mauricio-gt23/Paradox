package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.paradox.R
import com.example.paradox.models.Company
import com.example.paradox.network.CompaniesService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCompanyActivity : AppCompatActivity() {

    lateinit var company: Company

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)

        loadCompany()

        val btAddCompany = findViewById<Button>(R.id.btAddCompany)

        btAddCompany.setOnClickListener{
            addCompany()
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadCompany() {
        val gson = Gson()
        val stringObj = intent.getStringExtra("company")

        company = gson.fromJson(stringObj, Company::class.java) ?: Company(0, "", "", "",0,"", 0, 0)
    }

    private fun addCompany(){
        val request = CompaniesService.companiesInstance.addCompany(company)
        request.enqueue(object: Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("AddCompanyActivity","Error in Adding Company")
            }

            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val editedCompany = response.body()
                if (editedCompany != null) {
                    Log.d("AddCompanyActivity", editedCompany.toString())
                }
            }
        })
    }
}