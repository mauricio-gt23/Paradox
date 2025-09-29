package com.example.paradox.ui.addcompany

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.paradox.R
import com.example.paradox.controller.activities.SharedPreferences
import com.example.paradox.databinding.FragmentAddCompanyBinding
import com.example.paradox.models.Company
import com.example.paradox.models.RequestCompany
import com.example.paradox.models.Sector
import com.example.paradox.models.Sectors
import com.example.paradox.network.CompaniesService
import com.example.paradox.network.SectorsService
import com.example.paradox.ui.mycompanies.ViewCompaniesFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCompanyFragment : Fragment() {
    private lateinit var binding: FragmentAddCompanyBinding
    private lateinit var sectors : ArrayList<Sector>
    private var sectorId : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_company, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddCompanyBinding.bind(view)
        loadSectors()

        val sectorAdapter = ArrayAdapter(view.context, R.layout.prototype_sector, sectors)
        binding.atvSector.setAdapter(sectorAdapter)
        binding.atvSector.setOnItemClickListener { _, _, position, _ ->
            val selectedSector = sectorAdapter.getItem(position) as Sector
            sectorId = selectedSector.id
            binding.btAddCompany.isEnabled = true
        }

        binding.btAddCompany.setOnClickListener{
            addCompany(view)
            val fragment = ViewCompaniesFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
        }
    }

    private fun loadSectors() {
        sectors = ArrayList<Sector>()
        val request = SectorsService.sectorsInstance.getAllSectors()
        request.enqueue(object: Callback<Sectors> {
            override fun onFailure(call: Call<Sectors>, t: Throwable) {
                Log.d("Fragment Fail", "Error: $t")
            }

            override fun onResponse(call: Call<Sectors>, response: Response<Sectors>) {
                val content = response.body()
                if (content != null) {
                    Log.d("AddCompanyFragment", content.toString())
                    sectors.apply {
                        addAll(content.sectors)
                    }
                }
            }
        })
    }

    private fun addCompany(view: View){
        val sharedPreferences = context?.let { SharedPreferences(it) }
        var idd = 0
        if (sharedPreferences != null) {
            if(sharedPreferences.getValues("id") != null) {
                idd = sharedPreferences.getValues("id")!!.toInt()
            }
        }

        val requiredName = view.findViewById<EditText>(R.id.etAddName).text.toString()
        val requiredRuc = Integer.parseInt(view.findViewById<EditText>(R.id.etAddRuc).text.toString())
        val requiredAddress = view.findViewById<EditText>(R.id.etAddAddress).text.toString()
        val requiredDescription = view.findViewById<EditText>(R.id.etAddDescription).text.toString()
        val requiredLogo = view.findViewById<EditText>(R.id.etAddLogo).text.toString()

        val requestCompany = RequestCompany(
            requiredName,
            requiredDescription,
            requiredLogo,
            requiredRuc,
            requiredAddress
        )

        val request = CompaniesService.companiesInstance.addCompany(idd, sectorId, requestCompany)
        request.enqueue(object: Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("Fragment Fail", "Error: $t")
            }

            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val companyAdded = response.body()
                if (companyAdded != null) {
                    Log.d("AddCompanyFragment", companyAdded.toString())
                }
            }
        })
    }
}