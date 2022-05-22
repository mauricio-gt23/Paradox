package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.paradox.models.Company
import com.example.paradox.models.RequestCompany
import com.example.paradox.network.CompaniesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)

        val btAddCompany = findViewById<Button>(R.id.btAddCompany)

        btAddCompany.setOnClickListener{
            addCompany()
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addCompany(){
        //val employerId = intent.getIntExtra("employerId", 0)
        val employerId = 1
        // val sectorRequest Obtener Checkbox Seleccionado (Sector Object) -> sector.id
        val sectorId = 1

        val requiredName = findViewById<EditText>(R.id.etAddName).text.toString()
        val requiredRuc = Integer.parseInt(findViewById<EditText>(R.id.etAddRuc).text.toString())
        val requiredAddress = findViewById<EditText>(R.id.etAddAddress).text.toString()
        val requiredDescription = findViewById<EditText>(R.id.etAddDescription).text.toString()
        val requiredLogo = findViewById<EditText>(R.id.etAddLogo).text.toString()

        val requestCompany = RequestCompany(
            requiredName,
            requiredDescription,
            requiredLogo,
            requiredRuc,
            requiredAddress
        )

        val request = CompaniesService.companiesInstance.addCompany(employerId, sectorId, requestCompany)
        request.enqueue(object: Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("AddCompanyActivity","Error in Adding Company")
            }

            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val companyAdded = response.body()
                if (companyAdded != null) {
                    Log.d("AddCompanyActivity", companyAdded.toString())
                }
            }
        })
    }
}