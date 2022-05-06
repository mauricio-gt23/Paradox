package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_view_companies_employer.*

class ViewCompaniesEmployerActivity : AppCompatActivity(), OnItemClickListener {

    override fun OnItemClicked(company: Company) {
        val intent = Intent(this, ViewCompanyEmployerActivity::class.java)
        val gson = Gson()
        intent.putExtra("company", gson.toJson(company))
        startActivity(intent)
    }

    lateinit var companies: List<Company>
    lateinit var companyAdapter: CompanyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_companies_employer)
    }

    override fun onResume() {
        super.onResume()
        loadCompanies()

        companyAdapter = CompanyAdapter(companies, this)
        rvCompanies.adapter = companyAdapter
        rvCompanies.layoutManager = LinearLayoutManager(this)
    }

    private fun loadCompanies() {
        companies = AppDatabase.getInstance(this).getDao().getAll()
    }
}