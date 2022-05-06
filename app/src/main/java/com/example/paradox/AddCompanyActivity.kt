package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddCompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)

        val btAddCompany = findViewById<Button>(R.id.btAddCompany)

        btAddCompany.setOnClickListener{
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java)
            startActivity(intent)
        }
    }
}