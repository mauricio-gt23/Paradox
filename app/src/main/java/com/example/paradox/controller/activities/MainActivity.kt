package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import com.example.paradox.R
import com.example.paradox.models.EmployeerCaro
import com.example.paradox.models.User
import com.example.paradox.network.EmployeerService
import com.example.paradox.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = SharedPreferences(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btLogin = findViewById<Button>(R.id.btLogin)
        val btRegister = findViewById<Button>(R.id.btCreateAccount)

        btLogin.setOnClickListener {

            val tvErrorLogin = findViewById<TextView>(R.id.tvErrorLogin)
            val name = etUser.text.toString()
            val password = etPassword.text.toString()
            if (name.isNotBlank() && password.isNotBlank()) {
                loadUser()
                val id =  sharedPreferences.getValues("id")

                if(!id.isNullOrBlank()){


                    validateUser()
                }
            } else {
                tvErrorLogin.isInvisible = false
            }
        }

        btRegister.setOnClickListener {

            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


        //val languages = resources.getStringArray(R.array.languages)

    }

    private fun validateUser() {
        val sharedPreferences = SharedPreferences(this@MainActivity)


            val id =  sharedPreferences.getValues("id").toString().toLong()
            val request = EmployeerService.employeerInstance.getEmployeer(id)
            request.enqueue(object : Callback<EmployeerCaro> {
                override fun onFailure(call: Call<EmployeerCaro>, t: Throwable) {
                    Log.d("Employeer", "Error in Fetching EmployeerCaro")

                }
                override fun onResponse(call: Call<EmployeerCaro>, response: Response<EmployeerCaro>) {
                    if(response.isSuccessful){

                        val intent = Intent(this@MainActivity, AnnouncementActivity::class.java)
                        startActivity(intent)




                    }

                }
            })



    }

    private fun loadUser() {

        val sharedPreferences = SharedPreferences(this@MainActivity)

        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val name = etUser.text.toString()
        val password = etPassword.text.toString()
        val request = UserService.userInstance.getAuth(name, password)
        request.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("ga", "Err34r in Fetching Company")

            }
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user =  response.body()!!

                    sharedPreferences.save("id", user.id.toString())


                }

            }
        })
    }
}