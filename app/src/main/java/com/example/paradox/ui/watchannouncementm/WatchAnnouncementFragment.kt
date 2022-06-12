package com.example.paradox.ui.watchannouncementm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.OnItemClickListener
import com.example.paradox.adapter.PostulantAdapter
import com.example.paradox.adapter.PublishedWorkAdapter
import com.example.paradox.controller.activities.SharedPreferences
import com.example.paradox.databinding.FragmentWatchAnnouncementBinding
import com.example.paradox.models.*
import com.example.paradox.network.JobOfferService
import com.example.paradox.network.PostulantJobService
import com.example.paradox.network.PublishedWorkService
import com.example.paradox.ui.postulantprofilem.PostulantProfileFragment
import com.example.paradox.ui.publishedworksm.PublishedWorksFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WatchAnnouncementFragment : Fragment(), OnItemClickListener<Postul> {

    private lateinit var binding: FragmentWatchAnnouncementBinding

    lateinit var postulantAdapter: PostulantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista: View = inflater.inflate(R.layout.fragment_watch_announcement, container, false)
        fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        return vista
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWatchAnnouncementBinding.bind(view)
        val btBack2: ImageButton = view.findViewById<ImageButton>(R.id.btBack2)
        btBack2.setOnClickListener{
            val sharedPreferences = context?.let { SharedPreferences(it) }
            sharedPreferences?.removeValue("publishedWorkId")
            val fragment = PublishedWorksFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
        }
        loadPublishedWork(view)
    }

    private fun loadPublishedWork(view: View) {

        val sharedPreferences = context?.let { SharedPreferences(it) }
        var idd: Int = 0

        if (sharedPreferences != null) {
            if(sharedPreferences.getValues("publishedWorkId") != null) {
                idd = sharedPreferences.getValues("publishedWorkId")!!.toInt()
            }
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jobOfferService: JobOfferService = retrofit.create((JobOfferService::class.java))

        val request = jobOfferService.getJobOfferById(idd)

        request.enqueue(object: Callback<Work> {
            override fun onResponse(call: Call<Work>, response: Response<Work>) {
                if (response.isSuccessful) {
                    val content = response.body()
                    if (content != null) {
                        loadPostulants(view, content.id)
                        view.findViewById<TextView>(R.id.tvTitleJob).text = content.job
                        view.findViewById<TextView>(R.id.tvTimeJob).text = content.time
                        view.findViewById<TextView>(R.id.tvMoney).text = content.salary.toString()
                        view.findViewById<TextView>(R.id.tvDistrict).text = content.company
                        view.findViewById<TextView>(R.id.tvDes1).text = content.info
                    }
                }
            }
            override fun onFailure(call: Call<Work>, t: Throwable) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadPostulants(view: View, id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postulantJobService: PostulantJobService = retrofit.create((PostulantJobService::class.java))

        val request = postulantJobService.getAllPostulantJobsByJobOfferId(id)

        request.enqueue(object: Callback<Postulantz> {
            override fun onResponse(call: Call<Postulantz>, response: Response<Postulantz>) {
                if (response.isSuccessful) {
                    val rvPostulant = view.findViewById<RecyclerView>(R.id.rvPostulant2)
                    val content = response.body()
                    if (content != null) {
                        Log.d("WatchAnnouncementActivity", content.toString())
                        postulantAdapter = PostulantAdapter(content.postulantz, this@WatchAnnouncementFragment)
                        rvPostulant.adapter = postulantAdapter
                        rvPostulant.layoutManager = LinearLayoutManager(context)
                    }
                }
            }

            override fun onFailure(call: Call<Postulantz>, t: Throwable) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun OnItemClicked(obj: Postul) {

        val bundle = Bundle()
        bundle.putInt("postulId", obj.id)
        bundle.putString("firstname", obj.firstname)
        bundle.putString("lastname", obj.lastname)
        bundle.putString("document", obj.document)
        bundle.putString("email", obj.email)
        bundle.putInt("number", obj.number)
        val fragment = PostulantProfileFragment()
        fragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
    }

}