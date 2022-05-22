package com.example.paradox

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var works = ArrayList<Work>()
    var postulants = ArrayList<Postulant>()
    var workAdapter = WorkAdapter(works)
    var postulationAdapter = PostulationAdapter(works)
    var postulantAdapter = PostulantAdapter(postulants)
    private lateinit var drawer: DrawerLayout
    private lateinit var toogle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_announcement)
        loadWorks()
        loadPostulants()
        initView(4)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer=findViewById(R.id.drawer_layout)
        toogle = ActionBarDrawerToggle(this,drawer,toolbar ,R.string.navegation_drawer_open,R.string.navegation_drawer_close)
        drawer.addDrawerListener(toogle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_item_one-> Toast.makeText(this,"Item 1", Toast.LENGTH_SHORT).show()
            R.id.nav_item_two-> Toast.makeText(this,"Item 2", Toast.LENGTH_SHORT).show()
            R.id.nav_item_three-> Toast.makeText(this,"Item 3", Toast.LENGTH_SHORT).show()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onPostCreate(savedInstanceState: Bundle?){
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
