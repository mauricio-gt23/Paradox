package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_company.*

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

        company = gson.fromJson(stringObj, Company::class.java) ?: Company(null, "", "", "","","")
    }

    fun addCompany(){
        company.name = etAddName.text.toString()
        company.ruc = etAddRuc.text.toString()
        company.address = etAddAddress.text.toString()
        company.description = etAddDescription.text.toString()
        company.logo = etAddLogo.text.toString()

        AppDatabase.getInstance(this).getDao().insertCompany(company)

        finish()
    }
}