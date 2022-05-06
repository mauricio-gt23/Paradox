package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class ViewCompanyEmployerActivity : AppCompatActivity() {

    lateinit var ivLogoDetail: ImageView
    lateinit var tvNameDetail: TextView
    lateinit var tvRucDetail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_company_employer)

        ivLogoDetail = findViewById(R.id.ivLogo)
        tvNameDetail = findViewById(R.id.tvNameDetail)
        tvRucDetail = findViewById(R.id.tvRucDetail)

        val btBack1=findViewById<ImageButton>(R.id.btBack1)
        val btSaveEditedCompany=findViewById<Button>(R.id.btSaveEditedCompany)

        btBack1.setOnClickListener{
            val intent = Intent(this, ViewCompanyEmployerActivity::class.java)
            startActivity(intent)
        }
        btSaveEditedCompany.setOnClickListener {
            val intent = Intent(this, EditCompanyActivity::class.java)
            startActivity(intent)
        }
    }
}