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
import com.example.paradox.models.RequestCompany
import com.example.paradox.network.CompaniesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_company)

        loadCompany()

        val btBack2 = findViewById<ImageButton>(R.id.btBack2)
        val btSaveEditedCompany = findViewById<Button>(R.id.btSaveEditedCompany)

        btBack2.setOnClickListener{
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java) //TODO: cambiar activity y corregir
            startActivity(intent)
        }

        btSaveEditedCompany.setOnClickListener{
            editCompany()
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loadCompany() {
        val etEditName = findViewById<EditText>(R.id.etEditName)
        val etEditRuc = findViewById<EditText>(R.id.etEditRuc)
        val etEditAddress = findViewById<EditText>(R.id.etEditAddress)
        val etEditDescription = findViewById<EditText>(R.id.etEditDescription)
        val etEditLogo = findViewById<EditText>(R.id.etEditLogo)

        if (intent.extras != null){
            etEditName.setText(intent.getStringExtra("name"))
            etEditRuc.setText(intent.getIntExtra("ruc", 0).toString())
            etEditAddress.setText(intent.getStringExtra("direccion"))
            etEditDescription.setText(intent.getStringExtra("description"))
            etEditLogo.setText(intent.getStringExtra("logo"))
        }
    }

    private fun editCompany() {
        val employerId = intent.getIntExtra("employerId", 0)
        val sectorId = intent.getIntExtra("sectorId", 0)

        val requiredName = findViewById<EditText>(R.id.etEditName).text.toString()
        val requiredRuc = Integer.parseInt(findViewById<EditText>(R.id.etEditRuc).text.toString())
        val requiredAddress = findViewById<EditText>(R.id.etEditAddress).text.toString()
        val requiredDescription = findViewById<EditText>(R.id.etEditDescription).text.toString()
        val requiredLogo = findViewById<EditText>(R.id.etEditLogo).text.toString()

        val requestCompany = RequestCompany(
            requiredName,
            requiredDescription,
            requiredLogo,
            requiredRuc,
            requiredAddress
        )

        val request = CompaniesService.companiesInstance.editCompany(employerId, sectorId, requestCompany)
        request.enqueue(object: Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("EditCompanyActivity","Error in Editing Company")
            }

            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val companyEdited = response.body()
                if (companyEdited != null) {
                    Log.d("EditCompanyActivity", companyEdited.toString())
                }
            }
        })
    }
}