package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class EditCompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_company)

        val btBack2 = findViewById<ImageButton>(R.id.btBack2)
        val btSaveEditedCompany = findViewById<Button>(R.id.btSaveEditedCompany)

        btBack2.setOnClickListener{
            val intent = Intent(this, ViewCompanyEmployerActivity::class.java)
            startActivity(intent)
        }

        btSaveEditedCompany.setOnClickListener{
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java)
            startActivity(intent)
        }
    }
}