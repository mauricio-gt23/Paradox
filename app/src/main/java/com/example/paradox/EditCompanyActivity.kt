package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit_company.*

class EditCompanyActivity : AppCompatActivity() {

    lateinit var company: Company

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_company)

        loadCompany()

        val btBack2 = findViewById<ImageButton>(R.id.btBack2)
        val btEditCompany = findViewById<Button>(R.id.btEditCompany)

        btBack2.setOnClickListener{
            val intent = Intent(this, ViewCompanyEmployerActivity::class.java)
            startActivity(intent)
        }

        btEditCompany.setOnClickListener{
            editCompany()
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loadCompany() {
        val gson = Gson()
        val stringObj = intent.getStringExtra("company")

        company = gson.fromJson(stringObj, Company::class.java) ?: Company(null, "", "", "","","")

        if (company.id != null){
            etEditName.setText(company.name)
            etEditRuc.setText(company.ruc)
            etEditAddress.setText(company.address)
            etEditDescription.setText(company.description)
            etEditLogo.setText(company.logo)
        }
    }

    fun editCompany(){
        company.name = etEditName.text.toString()
        company.ruc = etEditRuc.text.toString()
        company.address = etEditAddress.text.toString()
        company.description = etEditDescription.text.toString()
        company.logo = etEditLogo.text.toString()

        AppDatabase.getInstance(this).getDao().updateCompany(company)

        finish()
    }
}