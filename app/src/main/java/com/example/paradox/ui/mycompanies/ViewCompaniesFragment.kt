package com.example.paradox.ui.mycompanies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.CompanyAdapter
import com.example.paradox.adapter.OnItemClickListener
import com.example.paradox.controller.activities.SharedPreferences
import com.example.paradox.databinding.FragmentViewCompaniesBinding
import com.example.paradox.models.Companies
import com.example.paradox.models.Company
import com.example.paradox.network.CompaniesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCompaniesFragment : Fragment(), OnItemClickListener<Company> {
    private lateinit var binding: FragmentViewCompaniesBinding
    lateinit var companyAdapter : CompanyAdapter
    private var employerId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_companies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewCompaniesBinding.bind(view)
        loadCompanies(view)
    }


    private fun loadCompanies(view: View) {
        val sharedPreferences = context?.let { SharedPreferences(it) }
        if (sharedPreferences != null) {
            if(sharedPreferences.getValues("id") != null) {
                employerId = sharedPreferences.getValues("id")!!.toInt()
            }
        }

        val request = CompaniesService.companiesInstance.getAllCompaniesByEmployerId(employerId)
        request.enqueue(object: Callback<Companies> {
            override fun onFailure(call: Call<Companies>, t: Throwable) {
                Log.d("Fragment Fail", "Error: $t")
            }

            override fun onResponse(call: Call<Companies>, response: Response<Companies>) {
                val rvCompanies = view.findViewById<RecyclerView>(R.id.rvCompanies)
                val content = response.body()
                if (content != null) {
                    Log.d("ViewCompaniesFragment", content.toString())
                    companyAdapter = CompanyAdapter(content.companies, view.context, this@ViewCompaniesFragment)
                    rvCompanies.adapter = companyAdapter
                    rvCompanies.layoutManager = LinearLayoutManager(context)
                }
            }
        })
    }

    override fun OnItemClicked(obj: Company) {
        val bundle = Bundle()
        bundle.putInt("employerId", employerId)
        bundle.putInt("companyId", obj.id)
        val fragment = ViewCompanyFragment()
        fragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
    }
}