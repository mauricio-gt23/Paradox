package com.example.paradox.controller.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isInvisible
import androidx.drawerlayout.widget.DrawerLayout
import com.example.paradox.R
import com.example.paradox.models.User
import com.example.paradox.network.UserService
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toogle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btLogin = findViewById<Button>(R.id.btLogin)
        val btRegister = findViewById<Button>(R.id.btCreateAccount)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer=findViewById(R.id.drawer_layout)
        toogle = ActionBarDrawerToggle(this,drawer,toolbar ,R.string.navegation_drawer_open,R.string.navegation_drawer_close)
        drawer.addDrawerListener(toogle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        btLogin.setOnClickListener {

            val tvErrorLogin = findViewById<TextView>(R.id.tvErrorLogin)
            val name = etUser.text.toString()
            val password = etPassword.text.toString()
            if (name.isNotBlank() && password.isNotBlank()) {
                loadUser()
                Toast.makeText(this, "Bienvenido ${name}", Toast.LENGTH_SHORT).show()


            } else {
                tvErrorLogin.isInvisible = false
            }


        }


        btRegister.setOnClickListener {

            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }



        val languages = resources.getStringArray(R.array.languages)

    }

    private fun loadUser() {
        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val name = etUser.text.toString()
        val password = etPassword.text.toString()
        val request = UserService.userInstance.getAuth(name,password)
        request.enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("ga","Error in Fetching Company")
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {

            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_item_one->Toast.makeText(this,"Item 1", Toast.LENGTH_SHORT).show()
            R.id.nav_item_two->Toast.makeText(this,"Item 2", Toast.LENGTH_SHORT).show()
            R.id.nav_item_three->Toast.makeText(this,"Item 3", Toast.LENGTH_SHORT).show()
            R.id.nav_item_four->Toast.makeText(this,"Item 4", Toast.LENGTH_SHORT).show()
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