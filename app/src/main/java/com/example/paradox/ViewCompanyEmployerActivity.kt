package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.paradox.models.Company
import com.example.paradox.network.CompaniesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCompanyEmployerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_company_employer)

        loadCompany()

        val btBack1 = findViewById<ImageButton>(R.id.btBack1)
        val btEditCompany = findViewById<Button>(R.id.btEditCompany)

        btBack1.setOnClickListener{
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java)
            startActivity(intent)
        }
        btEditCompany.setOnClickListener {
            val intent = Intent(this, EditCompanyActivity::class.java)
            intent.putExtra("employerId", 1) //cambiar value despues
            intent.putExtra("companyId", 1)
            startActivity(intent)
        }
    }

    private fun loadCompany() {
        val request = CompaniesService.companiesInstance.getCompanyById(1)
        request.enqueue(object: Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("ViewCompanyEmployerActivity","Error in Fetching Company")
            }

            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val tvNameDetail = findViewById<TextView>(R.id.tvNameDetail)
                val ivLogo = findViewById<ImageView>(R.id.ivLogo)
                val tvSectorDetail = findViewById<TextView>(R.id.tvSectorDetail)
                val tvRucDetail = findViewById<TextView>(R.id.tvRucDetail)
                val tvDescriptionDetail = findViewById<TextView>(R.id.tvDescriptionDetail)
                val tvAddressDetail = findViewById<TextView>(R.id.tvAddressDetail)

                val company = response.body()
                if (company != null) {
                    Log.d("ViewCompanyEmployerActivity", company.toString())
                    tvNameDetail.text = company.name
                    Glide.with(this@ViewCompanyEmployerActivity).load(company.logo).into(ivLogo)
                    tvSectorDetail.text  = company.idSector.toString()
                    tvRucDetail.text  = company.ruc.toString()
                    tvDescriptionDetail.text  = company.description
                    tvAddressDetail.text  = company.direccion
                }
            }
        })
    }
}