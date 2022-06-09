package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.OnItemClickListener
import com.example.paradox.adapter.PostulantAdapter
import com.example.paradox.models.Postul
import com.example.paradox.models.Postulantz
import com.example.paradox.models.PublishedWork
import com.example.paradox.network.PostulantJobService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WatchAnnouncementActivity : AppCompatActivity(), OnItemClickListener<Postul> {

    lateinit var publishedWork: PublishedWork
    lateinit var postulantAdapter: PostulantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_announcement)

        loadPublishedWork()
    }

    private fun loadPublishedWork() {
        val gson = Gson()
        val stringObj = intent.getStringExtra("publishedWork")

        publishedWork = gson.fromJson(stringObj, PublishedWork::class.java) ?: PublishedWork(null,"", "", "", "", "",null)

        if (publishedWork.id != null) {
            loadPostulants(publishedWork.id!!)
            findViewById<TextView>(R.id.tvTitleJob).text = publishedWork.job
            findViewById<TextView>(R.id.tvTimeJob).text = publishedWork.time
            findViewById<TextView>(R.id.tvMoney).text = publishedWork.salary.toString()
            findViewById<TextView>(R.id.tvDistrict).text = publishedWork.address
            findViewById<TextView>(R.id.tvDes1).text = publishedWork.description
        }
    }

    private fun loadPostulants(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postulantJobService: PostulantJobService = retrofit.create((PostulantJobService::class.java))

        val request = postulantJobService.getAllPostulantJobsByJobOfferId(id)

        request.enqueue(object: Callback<Postulantz> {
            override fun onResponse(call: Call<Postulantz>, response: Response<Postulantz>) {
                if (response.isSuccessful) {
                    val rvPostulant = findViewById<RecyclerView>(R.id.rvPostulant2)
                    val content = response.body()
                    if (content != null) {
                        Log.d("WatchAnnouncementActivity", content.toString())
                        postulantAdapter = PostulantAdapter(content.postulantz, this@WatchAnnouncementActivity)
                        rvPostulant.adapter = postulantAdapter
                        rvPostulant.layoutManager = LinearLayoutManager(this@WatchAnnouncementActivity)
                    }
                }
            }

            override fun onFailure(call: Call<Postulantz>, t: Throwable) {
                Toast.makeText(this@WatchAnnouncementActivity, "ERROR", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun OnItemClicked(postul: Postul) {
        val gson = Gson()
        val intent = Intent(this, PostulantProfileActivity::class.java).apply {
            putExtra("postul", gson.toJson(postul))
        }
        startActivity(intent)
    }

}