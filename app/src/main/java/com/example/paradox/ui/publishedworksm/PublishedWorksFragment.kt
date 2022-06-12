package com.example.paradox.ui.publishedworksm

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
import com.example.paradox.adapter.OnItemClickListener
import com.example.paradox.adapter.PublishedWorkAdapter
import com.example.paradox.controller.activities.SharedPreferences
import com.example.paradox.models.PublishedWork
import com.example.paradox.models.PublishedWorks
import com.example.paradox.network.PublishedWorkService
import com.example.paradox.ui.watchannouncementm.WatchAnnouncementFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PublishedWorksFragment : Fragment(), OnItemClickListener<PublishedWork> {

    lateinit var publishedWorkAdapter: PublishedWorkAdapter
    lateinit var rvPublishedWork: RecyclerView
    lateinit var publishedWork: ArrayList<PublishedWork>
    private var countPostulants: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val vista: View = inflater.inflate(R.layout.fragment_published_works, container, false)

        val svSearch = vista.findViewById<SearchView>(R.id.svSearchPublishedWork)

        loadPublishedWorks(vista)

        svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                publishedWorkAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        return vista
    }

    private fun loadPublishedWorks(view: View) {

        val editText = view.findViewById<TextView>(R.id.tvCountPublishedWorks)
        val sharedPreferences = context?.let { SharedPreferences(it) }
        var idd: Int = 0

        if (sharedPreferences != null) {
            if(sharedPreferences.getValues("id") != null) {
                idd = sharedPreferences.getValues("id")!!.toInt()
            }
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val publishedWorkService: PublishedWorkService = retrofit.create((PublishedWorkService::class.java))

        val request = publishedWorkService.getAllJobOffersByEmployeerId(idd)

        request.enqueue(object: Callback<PublishedWorks> {
            override fun onResponse(call: Call<PublishedWorks>, response: Response<PublishedWorks>) {
                if (response.isSuccessful) {
                    rvPublishedWork = view.findViewById<RecyclerView>(R.id.rvPublishedWork)
                    val content = response.body()
                    if (content != null) {
                        editText.text = content.publishedWorks.size.toString()
                        publishedWork = content.publishedWorks as ArrayList<PublishedWork>
                        publishedWorkAdapter = PublishedWorkAdapter(publishedWork, this@PublishedWorksFragment)
                        rvPublishedWork.adapter = publishedWorkAdapter
                        rvPublishedWork.layoutManager = LinearLayoutManager(context)
                        rvPublishedWork.setHasFixedSize(true)
                    }
                }
            }

            override fun onFailure(call: Call<PublishedWorks>, t: Throwable) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun OnItemClicked(obj: PublishedWork) {

        val bundle = Bundle()
        val sharedPreferences = context?.let { SharedPreferences(it) }
        if (sharedPreferences != null) {
            sharedPreferences.save("publishedWorkId", obj.id.toString())
        }
        val fragment = WatchAnnouncementFragment()
        fragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
    }
}