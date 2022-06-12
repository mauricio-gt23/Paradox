package com.example.paradox.ui.announcement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.WorkAdapter
import com.example.paradox.databinding.FragmentAnnouncementBinding
import com.example.paradox.models.Work
import com.example.paradox.models.Works
import com.example.paradox.network.JobOfferService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class AnnouncementFragment : Fragment() {

    private lateinit var binding: FragmentAnnouncementBinding
    lateinit var workAdapter: WorkAdapter
    lateinit var rvWork: RecyclerView
    lateinit var works: ArrayList<Work>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vista: View = inflater.inflate(R.layout.fragment_announcement, container, false)
        return vista
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnnouncementBinding.bind(view)

        loadWorks(view)

        val svSearch = view.findViewById<SearchView>(R.id.svSearch)

        svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                workAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
    }

    private fun loadWorks(view: View) {

        val editText = view.findViewById<TextView>(R.id.tvCountOffers)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jobOfferService: JobOfferService = retrofit.create((JobOfferService::class.java))

        val request = jobOfferService.getAllJobOffers()

        request.enqueue(object : Callback<Works> {
            override fun onResponse(call: Call<Works>, response: Response<Works>) {
                if (response.isSuccessful) {
                    rvWork = view.findViewById(R.id.rvWork)
                    val content = response.body()
                    if (content != null) {
                        editText.text = content.works.size.toString()
                        works = content.works as ArrayList<Work>
                        workAdapter = WorkAdapter(works)
                        rvWork.adapter = workAdapter
                        rvWork.layoutManager = LinearLayoutManager(context)
                        rvWork.setHasFixedSize(true)
                    }
                }
            }
            override fun onFailure(call: Call<Works>, t: Throwable) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
            }
        })
    }
}