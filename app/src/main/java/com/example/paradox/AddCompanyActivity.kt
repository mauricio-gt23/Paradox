package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.paradox.models.*
import com.example.paradox.network.CompaniesService
import com.example.paradox.network.SectorsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCompanyActivity : AppCompatActivity() {
    var employerId : Int = 2
    var sectorId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)

        val sectors = loadSectors()
        val sectorAdapter = ArrayAdapter<Sector>(this@AddCompanyActivity,R.layout.prototype_sector, sectors)
        val atvSector = findViewById<AutoCompleteTextView>(R.id.atvSector)
        atvSector.setAdapter(sectorAdapter)

        atvSector.setOnItemClickListener { _, _, position, _ ->
            val selectedSector = sectorAdapter.getItem(position) as Sector
            sectorId = selectedSector.id
        }

        val btAddCompany = findViewById<Button>(R.id.btAddCompany)

        btAddCompany.setOnClickListener{
            addCompany()
            val intent = Intent(this, ViewCompaniesEmployerActivity::class.java)
            intent.putExtra("employerId", employerId)
            startActivity(intent)
        }
    }

    private fun loadSectors(): ArrayList<Sector> {
        val sectors = ArrayList<Sector>()
        val request = SectorsService.sectorsInstance.getAllSectors()
        request.enqueue(object: Callback<Sectors> {
            override fun onFailure(call: Call<Sectors>, t: Throwable) {
                Log.d("AddCompanyActivity","Error in Fetching Sectors")
            }

            override fun onResponse(call: Call<Sectors>, response: Response<Sectors>) {
                val content = response.body()
                if (content != null) {
                    Log.d("AddCompanyActivity", content.toString())
                    sectors.apply {
                        addAll(content.sectors)
                    }
                }
            }
        })
        return sectors
    }

    private fun addCompany(){
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