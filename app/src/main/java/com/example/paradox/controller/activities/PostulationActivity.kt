package com.example.paradox.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.PostulationAdapter
import com.example.paradox.models.PostulantJobs
import com.example.paradox.network.PostulantJobService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostulationActivity : AppCompatActivity() {

    lateinit var postulationAdapter : PostulationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postulation)

        loadPostulations()

    }

    private fun loadPostulations() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postulantJobService: PostulantJobService = retrofit.create((PostulantJobService::class.java))

        val request = postulantJobService.getAllPostulantJobByPostulantId(4)

        request.enqueue(object: Callback<PostulantJobs> {
            override fun onResponse(call: Call<PostulantJobs>, response: Response<PostulantJobs>) {
                if (response.isSuccessful) {
                    val rvPostulation = findViewById<RecyclerView>(R.id.rvPostulation)
                    val content = response.body()
                    if (content != null) {
                        Log.d("PostulationActivity", content.toString())
                        postulationAdapter = PostulationAdapter(content.postulantJobs)
                        rvPostulation.adapter = postulationAdapter
                        rvPostulation.layoutManager = LinearLayoutManager(this@PostulationActivity)
                    }
                }
            }
            override fun onFailure(call: Call<PostulantJobs>, t: Throwable) {
                Toast.makeText(this@PostulationActivity, "ERROR", Toast.LENGTH_LONG).show()
            }

        })


    }
}