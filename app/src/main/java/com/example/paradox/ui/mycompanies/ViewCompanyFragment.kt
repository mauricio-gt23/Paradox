package com.example.paradox.ui.mycompanies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.paradox.R
import com.example.paradox.databinding.FragmentViewCompanyBinding
import com.example.paradox.models.Company
import com.example.paradox.network.CompaniesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCompanyFragment : Fragment() {
    private lateinit var binding: FragmentViewCompanyBinding
    //This data is for Edit Activity
    private var employerId : Int = 0
    private var companyId : Int = 0
    var sectorId : Int = 0
    var name : String = ""
    var logo : String = ""
    var ruc : Int = 0
    var description : String = ""
    var direccion : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            employerId = it.getInt("employerId")
            companyId = it.getInt("companyId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_company, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewCompanyBinding.bind(view)
        loadCompany(view)

        binding.btBack1.setOnClickListener{
            val fragment = ViewCompaniesFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
        }
        binding.btEditCompany.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("employerId", employerId)
            bundle.putInt("companyId", companyId)
            bundle.putInt("sectorId", sectorId)
            bundle.putString("name", name)
            bundle.putString("logo", logo)
            bundle.putInt("ruc", ruc)
            bundle.putString("description",description)
            bundle.putString("direccion", direccion)
            val fragment = EditCompanyFragment()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
        }
    }

    private fun loadCompany(view: View) {
        val request = CompaniesService.companiesInstance.getCompanyById(companyId)
        request.enqueue(object: Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("Fragment Fail", "Error: $t")
            }

            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val tvNameDetail = view.findViewById<TextView>(R.id.tvNameDetail)
                val ivLogo = view.findViewById<ImageView>(R.id.ivLogo)
                val tvSectorDetail = view.findViewById<TextView>(R.id.tvSectorDetail)
                val tvRucDetail = view.findViewById<TextView>(R.id.tvRucDetail)
                val tvDescriptionDetail = view.findViewById<TextView>(R.id.tvDescriptionDetail)
                val tvAddressDetail = view.findViewById<TextView>(R.id.tvAddressDetail)

                val companyDetail = response.body()
                if (companyDetail != null) {
                    Log.d("ViewCompanyFragment", companyDetail.toString())
                    tvNameDetail.text = companyDetail.name
                    Glide.with(view.context).load(companyDetail.logo).into(ivLogo)
                    tvSectorDetail.text  = companyDetail.nameSector
                    tvRucDetail.text  = companyDetail.ruc.toString()
                    tvDescriptionDetail.text  = companyDetail.description
                    tvAddressDetail.text  = companyDetail.direccion

                    //This data is for Edit Activity
                    sectorId = companyDetail.idSector
                    name = companyDetail.name
                    logo = companyDetail.logo
                    ruc  = companyDetail.ruc
                    description  = companyDetail.description
                    direccion  = companyDetail.direccion
                }
            }
        })
    }
}