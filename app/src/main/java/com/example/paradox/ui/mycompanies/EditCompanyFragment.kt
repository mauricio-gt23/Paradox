package com.example.paradox.ui.mycompanies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.paradox.R
import com.example.paradox.databinding.FragmentEditCompanyBinding
import com.example.paradox.models.Company
import com.example.paradox.models.RequestCompany
import com.example.paradox.network.CompaniesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCompanyFragment : Fragment() {
    private lateinit var binding: FragmentEditCompanyBinding
    private var employerId: Int = 0
    private var sectorId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            employerId = it.getInt("employerId")
            sectorId = it.getInt("companyId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_company, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditCompanyBinding.bind(view)
        arguments?.let {
            binding.etEditName.setText(it.getString("name"))
            binding.etEditRuc.setText(it.getInt("ruc").toString())
            binding.etEditAddress.setText(it.getString("direccion"))
            binding.etEditDescription.setText(it.getString("description"))
            binding.etEditLogo.setText(it.getString("logo"))
        }

        binding.btBack2.setOnClickListener{
            val fragment = ViewCompaniesFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
        }

        binding.btSaveEditedCompany.setOnClickListener{
            editCompany(view)
            val fragment = ViewCompaniesFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
        }
    }

    private fun editCompany(view: View) {
        val requiredName = view.findViewById<EditText>(R.id.etEditName).text.toString()
        val requiredRuc = Integer.parseInt(view.findViewById<EditText>(R.id.etEditRuc).text.toString())
        val requiredAddress = view.findViewById<EditText>(R.id.etEditAddress).text.toString()
        val requiredDescription = view.findViewById<EditText>(R.id.etEditDescription).text.toString()
        val requiredLogo = view.findViewById<EditText>(R.id.etEditLogo).text.toString()

        val requestCompany = RequestCompany(
            requiredName,
            requiredDescription,
            requiredLogo,
            requiredRuc,
            requiredAddress
        )

        val request = CompaniesService.companiesInstance.editCompany(employerId, sectorId, requestCompany)
        request.enqueue(object: Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("Fragment Fail", "Error: $t")
            }

            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val companyEdited = response.body()
                if (companyEdited != null) {
                    Log.d("EditCompanyFragment", companyEdited.toString())
                }
            }
        })
    }
}