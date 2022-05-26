package com.example.paradox.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.PostulantAdapter
import com.example.paradox.adapter.WorkAdapter
import com.example.paradox.models.Work
import com.example.paradox.models.Works
import com.example.paradox.network.JobOfferService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnnouncementActivity : AppCompatActivity() {

    var works: ArrayList<Work> = ArrayList()
    var workAdapter = WorkAdapter(works)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)

        initView(1)
        loadWorks()


    }

    private fun initView(value: Int) {
        val rvWork = findViewById<RecyclerView>(R.id.rvWork)
        rvWork.adapter = workAdapter
        rvWork.layoutManager = LinearLayoutManager(this)
    }

    private fun loadWorks() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jobOfferService: JobOfferService = retrofit.create((JobOfferService::class.java))

        val request = jobOfferService.getAllJobOffers()

        request.enqueue(object : Callback<Works> {
            override fun onResponse(call: Call<Works>, response: Response<Works>) {
                if (response.isSuccessful) {
                    val content = response.body()
                    if (content != null) {
                        findViewById<TextView>(R.id.tvCountOffers).text = content.works.size.toString()
                        for (i in content.works.indices) {
                            val subtitle = content.works.get(i).subtitle
                            val job = content.works.get(i).job
                            val time = content.works.get(i).time
                            val info = content.works.get(i).info
                            val work = Work("Claro", subtitle, job, time, info)
                            works.add(work)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Works>, t: Throwable) {
                Toast.makeText(this@AnnouncementActivity, "ERROR", Toast.LENGTH_LONG).show()
            }
        })
    }
}
