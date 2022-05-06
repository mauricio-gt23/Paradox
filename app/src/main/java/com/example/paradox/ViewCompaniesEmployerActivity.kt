package com.example.paradox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewCompaniesEmployerActivity : AppCompatActivity() {
    val REQUEST_CODE = 0
    val companies = ArrayList<Company>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_companies_employer)
        loadCompanies()
        initView()
    }

    private fun initView() {
        val rvCompanies = findViewById<RecyclerView>(R.id.rvCompanies)
        rvCompanies. layoutManager = LinearLayoutManager(this)
    }

    private fun loadCompanies() {
        companies.add(Company(1,
            "CLaro",
            20467534026,
            "Av. Nicolás Arriola N° 480, La Victoria",
            "Claro Perú es subsidiaria de América Móvil, S.A.B. de C.V. y opera en el país desde el 10 de agosto de 2005.",
            "https://upload.wikimedia.org/wikipedia/commons/0/0c/Claro.svg"
        ))
        companies.add(Company(1,
            "Movistar",
            20467534021,
            "Av. Marina N° 480, San Miguel",
            "Movistar Perú es la operadora de confianza de todos los peruanos.",
            "https://logos-marcas.com/wp-content/uploads/2020/04/Movistar-Simbolo.png"
        ))
    }
}