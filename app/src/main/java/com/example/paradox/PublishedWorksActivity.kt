package com.example.paradox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.adapter.OnItemClickListener
import com.example.paradox.adapter.PublishedWorkAdapter
import com.example.paradox.controller.activities.WatchAnnouncementActivity
import com.example.paradox.models.PublishedWork
import com.example.paradox.models.PublishedWorks
import com.example.paradox.network.PublishedWorkService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PublishedWorksActivity : AppCompatActivity(), OnItemClickListener<PublishedWork> {

    lateinit var publishedWorkAdapter: PublishedWorkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_published_works)

        loadPublishedWorks()
    }

    private fun loadPublishedWorks() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val publishedWorkService: PublishedWorkService = retrofit.create((PublishedWorkService::class.java))

        val request = publishedWorkService.getAllJobOffersByEmployeerId(1)

        request.enqueue(object: Callback<PublishedWorks> {
            override fun onResponse(call: Call<PublishedWorks>, response: Response<PublishedWorks>) {
                if (response.isSuccessful) {
                    val rvPublishedWork = findViewById<RecyclerView>(R.id.rvPublishedWork)
                    val content = response.body()
                    if (content != null) {
                        Log.d("PublishedWorkActivity", content.toString())
                        publishedWorkAdapter = PublishedWorkAdapter(content.publishedWorks, this@PublishedWorksActivity)
                        rvPublishedWork.adapter = publishedWorkAdapter
                        rvPublishedWork.layoutManager = LinearLayoutManager(this@PublishedWorksActivity)
                    }
                }
            }

            override fun onFailure(call: Call<PublishedWorks>, t: Throwable) {
                Toast.makeText(this@PublishedWorksActivity, "ERROR", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun OnItemClicked(publishedWork: PublishedWork) {
        val gson = Gson()
        val intent = Intent(this, WatchAnnouncementActivity::class.java).apply {
            putExtra("publishedWork", gson.toJson(publishedWork))
        }
        startActivity(intent)
    }


}

