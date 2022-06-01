package com.example.paradox.ui.publishedworksm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.OnItemClickListener
import com.example.paradox.adapter.PublishedWorkAdapter
import com.example.paradox.controller.activities.SharedPreferences
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PublishedWorksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PublishedWorksFragment : Fragment(), OnItemClickListener<PublishedWork> {

    lateinit var publishedWorkAdapter: PublishedWorkAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val vista: View = inflater.inflate(R.layout.fragment_published_works, container, false)

        loadPublishedWorks(vista)

        return vista
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PublishedWorksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PublishedWorksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun loadPublishedWorks(view: View) {

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
                    val rvPublishedWork = view.findViewById<RecyclerView>(R.id.rvPublishedWork)
                    val content = response.body()
                    if (content != null) {
                        Log.d("PublishedWorkActivity", content.toString())
                        publishedWorkAdapter = PublishedWorkAdapter(content.publishedWorks, this@PublishedWorksFragment)
                        rvPublishedWork.adapter = publishedWorkAdapter
                        rvPublishedWork.layoutManager = LinearLayoutManager(context)
                    }
                }
            }

            override fun onFailure(call: Call<PublishedWorks>, t: Throwable) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun OnItemClicked(publishedWork: PublishedWork) {
        val gson = Gson()
        val intent = Intent(context, WatchAnnouncementActivity::class.java).apply {
            putExtra("publishedWork", gson.toJson(publishedWork))
        }
        startActivity(intent)
    }
}