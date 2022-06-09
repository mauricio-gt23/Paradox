package com.example.paradox.controller.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.paradox.R

class MyAnnoucementActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_announcement)
    }

 ///NO BORRAR >:ccc
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_salir, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPreferences = SharedPreferences(this@MyAnnoucementActivity)
        sharedPreferences.clearSharedPreferences()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    ///NO BORRAR >:ccc
}