package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.CompanyAdapter
import com.example.paradox.models.Companies
import com.example.paradox.network.CompaniesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCompaniesEmployerActivity : AppCompatActivity(), CompanyAdapter.OnItemClickListener {
    lateinit var companyAdapter : CompanyAdapter
    var employerId: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_companies_employer)

        loadCompanies(employerId)
    }

    private fun loadCompanies(employerId: Int) {
        val request = CompaniesService.companiesInstance.getAllCompaniesByEmployerId(employerId)
        request.enqueue(object: Callback<Companies> {
            override fun onFailure(call: Call<Companies>, t: Throwable) {
                Log.d("ViewCompaniesEmployerActivity","Error in Fetching Companies")
            }

            override fun onResponse(call: Call<Companies>, response: Response<Companies>) {
                val rvCompanies = findViewById<RecyclerView>(R.id.rvCompanies)
                val content = response.body()
                if (content != null) {
                    Log.d("ViewCompaniesEmployerActivity", content.toString())
                    companyAdapter = CompanyAdapter(content.companies, this@ViewCompaniesEmployerActivity, this@ViewCompaniesEmployerActivity)
                    rvCompanies.adapter = companyAdapter
                    rvCompanies.layoutManager = LinearLayoutManager(this@ViewCompaniesEmployerActivity)
                }
            }
        })
    }

    override fun onItemClicked(id: Int) {
        val intent = Intent(this, ViewCompanyEmployerActivity::class.java)
        intent.putExtra("employerId", employerId)
        intent.putExtra("companyId", id)
        startActivity(intent)
    }
}