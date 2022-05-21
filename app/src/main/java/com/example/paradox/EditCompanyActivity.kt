package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
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

        val nameRequest = findViewById<EditText>(R.id.etEditName).text.toString()
        val rucRequest = Integer.parseInt(findViewById<EditText>(R.id.etEditRuc).text.toString())
        val addressRequest = findViewById<EditText>(R.id.etEditAddress).text.toString()
        val descriptionRequest = findViewById<EditText>(R.id.etEditDescription).text.toString()
        val logoRequest = findViewById<EditText>(R.id.etEditLogo).text.toString()

        val companyEdited = RequestCompany(
            nameRequest,
            descriptionRequest,
            logoRequest,
            rucRequest,
            addressRequest
        )

        val request = CompaniesService.companiesInstance.editCompany(sectorId, companyEdited)
        request.enqueue(object: Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("EditCompanyActivity","Error in Editing Company")
            }

            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val editedCompany = response.body()
                if (editedCompany != null) {
                    Log.d("EditCompanyActivity", editedCompany.toString())
                }
            }
        })
    }
}