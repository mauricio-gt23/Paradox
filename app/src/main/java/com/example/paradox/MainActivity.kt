package com.example.paradox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var works = ArrayList<Work>()
    var postulants = ArrayList<Postulant>()
    var workAdapter = WorkAdapter(works)
    var postulationAdapter = PostulationAdapter(works)
    var postulantAdapter = PostulantAdapter(postulants)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_announcement)
        loadWorks()
        loadPostulants()
        initView(4)
    }

    private fun initView(value: Int) {

        if (value == 1) {
            val rvWork = findViewById<RecyclerView>(R.id.rvWork)
            rvWork.adapter = workAdapter
            rvWork.layoutManager = LinearLayoutManager(this)
        } else if (value == 2) {
            val rvPostulation = findViewById<RecyclerView>(R.id.rvPostulation)
            rvPostulation.adapter = postulationAdapter
            rvPostulation.layoutManager = LinearLayoutManager(this)
        } else if (value == 3) {
            val rvPostulant = findViewById<RecyclerView>(R.id.rvPostulant)
            rvPostulant.adapter = postulantAdapter
            rvPostulant.layoutManager = LinearLayoutManager(this)
        }

    }

    private fun loadWorks() {
        works.add(Work("Claro", "Publicado el 10 de Enero", "Desarrollador Backend C#", "Contrato indefinido", "Nuestro cliente en tecnología de la información con más de 39 años en...", "Aceptado"))
        works.add(Work("Movistar", "Publicado el 04 de Abril", "Desarrollador Frontend", "Contrato indefinido", "Nuestro cliente", "Rechazado"))
    }

    private fun loadPostulants() {
        postulants.add(Postulant("Marcus Workman"))
        postulants.add(Postulant("Kaylynn Arcand"))
        postulants.add(Postulant("Alfredo Korsgaard"))
        postulants.add(Postulant("Maria Vaccaro"))
    }
}
