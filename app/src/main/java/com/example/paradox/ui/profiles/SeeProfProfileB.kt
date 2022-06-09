package com.example.paradox.ui.profiles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.LanguageAdapter
import com.example.paradox.adapter.SkillAdapter
import com.example.paradox.adapter.StudyAdapter
import com.example.paradox.controller.activities.EditProfessionalProfileP
import com.example.paradox.controller.activities.SharedPreferences
import com.example.paradox.models.*
import com.example.paradox.network.PostulantService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SeeProfProfileB.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeeProfProfileB : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var skillAdapter: SkillAdapter
    lateinit var languageAdapter: LanguageAdapter
    lateinit var studiesAdapter: StudyAdapter
    var profileId: Int? = null
    var skills = listOf<Skill>()
    var professionalProfilePostulant = ProfProfile()
    var idd: Int = 0

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
    ): View {
        // Inflate the layout for this fragment
        val vista: View = inflater.inflate(R.layout.fragment_see_prof_profile_b, container, false)
        val buttonEditProfessional = vista.findViewById<FloatingActionButton>(R.id.btGoToEditProfessional)
        loadProfProfileOfUser(vista)
        buttonEditProfessional.setOnClickListener {
            val intent = Intent(context, EditProfessionalProfileP::class.java)
            intent.putExtra("ProfProfile", this.professionalProfilePostulant)
            intent.putExtra("profileId", this.profileId)
            startActivity(intent)
        }
        return vista
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SeeProfProfileB().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun loadSkills(view: View) {
        val tvNoRegistersStudies = view.findViewById<TextView>(R.id.tvNoRegistersStudies)
        val tvNoRegistersSkills = view.findViewById<TextView>(R.id.tvNoRegistersSkills)
        val tvNoRegistersLanguages = view.findViewById<TextView>(R.id.tvNoRegistersLanguages)
        profileId?.let { PostulantService.postulantInstance.getSkillsByProfileId(profileId!!) }
            ?.enqueue(object : Callback<Skills> {
                override fun onResponse(call: Call<Skills>, response: Response<Skills>) {
                    if (response.isSuccessful) {
                        val rvSkills = view.findViewById<RecyclerView>(R.id.rcSkills)
                        val content = response.body()
                        if (content != null) {
                            skills = content.skills
                            skillAdapter = context?.let { SkillAdapter(content.skills, it) }!!
                            rvSkills.adapter = skillAdapter
                            rvSkills.layoutManager = LinearLayoutManager(context)
                            Log.d("skills ", skills.size.toString());
                            if (skills.size.toString() != "0") {
                                tvNoRegistersSkills.text = ""
                            }
//                            tvNoRegistersStudies.text = ""
//                            tvNoRegistersSkills.text = ""
//                            tvNoRegistersLanguages.text = ""
                        }
                    }
                }

                override fun onFailure(call: Call<Skills>, t: Throwable) {
                    Toast.makeText(
                        activity,
                        "Data could not be retrieved",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

        profileId?.let { PostulantService.postulantInstance.getStudiesByProfileId(it) }
            ?.enqueue(object : Callback<Studies> {
                override fun onResponse(call: Call<Studies>, response: Response<Studies>) {
                    if (response.isSuccessful) {
                        val rvStudies = view.findViewById<RecyclerView>(R.id.rcStudies)
                        val content = response.body()
                        if (content != null) {
                            studiesAdapter = context?.let { StudyAdapter(content.studies, it) }!!
                            rvStudies.adapter = studiesAdapter
                            rvStudies.layoutManager = LinearLayoutManager(context)
                            if (content.studies.size.toString() != "0") {
                                tvNoRegistersStudies.text = ""
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<Studies>, t: Throwable) {
                    Toast.makeText(context, "Data could not be retrieved", Toast.LENGTH_LONG).show()
                }
            })

        profileId?.let {
            PostulantService.postulantInstance.getLanguagesByProfileId(
                it
            )
        }?.enqueue(object : Callback<Languages> {
            override fun onResponse(call: Call<Languages>, response: Response<Languages>) {
                if (response.isSuccessful) {
                    val rvLanguages = view.findViewById<RecyclerView>(R.id.rcLanguages)
                    val content = response.body()
                    if (content != null) {
                        languageAdapter = context?.let { LanguageAdapter(content.languages, it) }!!
                        rvLanguages.adapter = languageAdapter
                        rvLanguages.layoutManager = LinearLayoutManager(context)
                        if (content.languages.size.toString() != "0") {
                            tvNoRegistersLanguages.text = ""
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Languages>, t: Throwable) {
                Toast.makeText(context, "Data could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadProfProfileOfUser(view: View) {
        val sharedPreferences = context?.let { SharedPreferences(it) }
        if (sharedPreferences != null) {
            if (sharedPreferences.getValues("id") != null) {
                idd = sharedPreferences.getValues("id")!!.toInt()
                Log.d("BrigProf", idd.toString());
                val request = PostulantService.postulantInstance.getProfileIdByUserId(idd)
                request.enqueue(object : Callback<ProfProfiles> {
                    override fun onResponse(
                        call: Call<ProfProfiles>,
                        response: Response<ProfProfiles>
                    ) {
                        if (response.isSuccessful) {
                            val profProfileRetrieved = response.body()?.profProfiles?.get(0)
                            Log.d("Profile ", profProfileRetrieved.toString())
                            if (profProfileRetrieved != null) {
                                profileId = profProfileRetrieved.id
                                Log.d("BrigProf", "id del perfil "+ profileId.toString());
                                loadProfessionalProfile(view, idd)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ProfProfiles>, t: Throwable) {
                        Toast.makeText(context, "Data could not be retrieved", Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
        }
    }
    fun loadProfessionalProfile(view: View, userId: Int) {
        val tvOccupationTextProfProfile =
            view.findViewById<TextView>(R.id.tvOcupacionTextProfProfile)
        val tvDescriptionTextProfProfile =
            view.findViewById<TextView>(R.id.tvDescriptionTextProfProfile)

        profileId?.let {
            PostulantService.postulantInstance.getProfileByIdAndPostulantId(
                userId,
                profileId!!
            )
        }?.enqueue(object : Callback<ProfProfile> {
            override fun onResponse(call: Call<ProfProfile>, response: Response<ProfProfile>) {
                if (response.isSuccessful) {
                    val profProfileRetrieved = response.body()
                    if (profProfileRetrieved != null) {
                        tvOccupationTextProfProfile.text = profProfileRetrieved.ocupation
                        tvDescriptionTextProfProfile.text = profProfileRetrieved.description
                        professionalProfilePostulant = ProfProfile(
                            profProfileRetrieved.id,
                            profProfileRetrieved.ocupation,
                            profProfileRetrieved.video,
                            profProfileRetrieved.description
                        )
                        loadSkills(view)
                    }
                }
            }

            override fun onFailure(call: Call<ProfProfile>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Data could not be retrieved",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

}