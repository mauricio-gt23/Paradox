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
import com.example.paradox.adapter.CompanyAdapter
import com.example.paradox.models.Company
import com.example.paradox.network.CompaniesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class ViewCompanyEmployerActivity : AppCompatActivity() {
    //This data is for Edit Activity
    var employerId : Int = 0
    var sectorId : Int = 0
    var name : String = ""
    var logo : String = ""
    var nameSector : String = ""
    var ruc : Int = 0
    var description : String = ""
    var direccion : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_company_employer)
        if (intent.extras != null) {
            val companyId : Int = intent.getIntExtra("companyId", 0)
            loadCompany(companyId)
        }

        val btBack1 = findViewById<ImageButton>(R.id.btBack1)
        val btEditCompany = findViewById<Button>(R.id.btEditCompany)

        btBack1.setOnClickListener{
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java)
            startActivity(intent)
        }
        btEditCompany.setOnClickListener {
            val intent = Intent(this, EditCompanyActivity::class.java)
            intent.putExtra("employerId", employerId)
            intent.putExtra("sectorId", sectorId)
            intent.putExtra("name", name)
            intent.putExtra("logo", logo)
            intent.putExtra("nameSector", nameSector)
            intent.putExtra("ruc", ruc)
            intent.putExtra("description", description)
            intent.putExtra("direccion", direccion)
            startActivity(intent)
        }
    }

    private fun loadCompany(companyId: Int) {
        val request = CompaniesService.companiesInstance.getCompanyById(companyId)
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
                    tvSectorDetail.text  = company.nameSector
                    tvRucDetail.text  = company.ruc.toString()
                    tvDescriptionDetail.text  = company.description
                    tvAddressDetail.text  = company.direccion

                    //This data is for Edit Activity
                    employerId = company.idEmployeer
                    sectorId = company.idSector
                    name = company.name
                    logo = company.logo
                    nameSector = company.nameSector
                    ruc  = company.ruc
                    description  = company.description
                    direccion  = company.direccion
                }
            }
        })
    }
}