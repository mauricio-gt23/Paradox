package com.example.paradox.ui.postulationm

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
import com.example.paradox.adapter.PostulationAdapter
import com.example.paradox.controller.activities.SharedPreferences
import com.example.paradox.models.PostulantJobs
import com.example.paradox.network.PostulantJobService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostulationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostulationFragment : Fragment() {

    lateinit var postulationAdapter : PostulationAdapter

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
        val vista: View = inflater.inflate(R.layout.fragment_postulation, container, false)

        loadPostulations(vista)

        return vista
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostulationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostulationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun loadPostulations(view: View) {

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

        val postulantJobService: PostulantJobService = retrofit.create((PostulantJobService::class.java))

        val request = postulantJobService.getAllPostulantJobByPostulantId(idd)

        request.enqueue(object: Callback<PostulantJobs> {
            override fun onResponse(call: Call<PostulantJobs>, response: Response<PostulantJobs>) {
                if (response.isSuccessful) {
                    val rvPostulation = view.findViewById<RecyclerView>(R.id.rvPostulation)
                    val content = response.body()
                    if (content != null) {
                        Log.d("PostulationActivity", content.toString())
                        postulationAdapter = PostulationAdapter(content.postulantJobs)
                        rvPostulation.adapter = postulationAdapter
                        rvPostulation.layoutManager = LinearLayoutManager(context)
                    }
                }
            }
            override fun onFailure(call: Call<PostulantJobs>, t: Throwable) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
            }

        })
    }
}