package com.example.paradox.ui.profiles

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.paradox.NavigationPostulantActivity
import com.example.paradox.R
import com.example.paradox.controller.activities.SharedPreferences
import com.example.paradox.databinding.FragmentEditProfProfileBinding
import com.example.paradox.databinding.FragmentSeeProfProfileBBinding
import com.example.paradox.models.*
import com.example.paradox.network.PostulantService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditProfProfile : Fragment() {
    private lateinit var binding: FragmentEditProfProfileBinding
    var languages = listOf<Language>()
    var existentLanguages = listOf<Language>()
    var studies = listOf<Study>()
    var existentStudies = listOf<Study>()
    var skills = listOf<Skill>()
    var existentSkills = listOf<Skill>()
    var professionalProfilePostulant = ProfProfile()
    var profileId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            profileId = it.getInt("profileId")
            professionalProfilePostulant = it.getParcelable("ProfProfile")!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_prof_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfProfileBinding.bind(view)

        arguments?.let {
            binding.etDegreeEdit.setText(professionalProfilePostulant.ocupation)
            binding.etProfileDescriptionShow.setText(professionalProfilePostulant.description)
            if (profileId != null) {
                getData(profileId)
            }
        }

        binding.tvMultiSelectB.setOnClickListener{
            configMultiSelectSkills(view)
        }
        binding.tvMultiSelectStudies.setOnClickListener{
            configMultiSelectStudies(view)
        }
        binding.tvMultiSelectLenguages.setOnClickListener{
            configMultiSelectLanguages(view)
        }
        binding.btSaveProfProfile.setOnClickListener{
            saveEditedProfProfile(view)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfProfile().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun configMultiSelectSkills(view: View) {
        val checkedSkillsArray = BooleanArray(skills.size)
        val tvMultiSelect = view.findViewById<TextView>(R.id.tvMultiSelectB)
        val builder = context?.let { AlertDialog.Builder(it) }
        val skillsList = mutableListOf<String>()
        // String array for alert dialog multi choice items
        for (i in skills.indices) {
            skillsList.add(skills[i].name)
            for (j in existentSkills.indices) {
                if (skills[i].id == existentSkills[j].id) {
                    checkedSkillsArray[i] = true
                }
            }
        }
        val languagesArray: Array<String> = skillsList.toTypedArray()
        val colorsList = listOf(*languagesArray)
        builder?.setTitle("Select your skills")
        builder?.setMultiChoiceItems(languagesArray, checkedSkillsArray) { _, which, isChecked ->
            checkedSkillsArray[which] = isChecked
            val currentItem = colorsList[which]
            Toast.makeText(context, "$currentItem $isChecked", Toast.LENGTH_SHORT).show()
        }
        builder?.setPositiveButton("OK") { _, _ ->
            tvMultiSelect.hint = "Select your skills..."
            tvMultiSelect.text = ""
            for (i in checkedSkillsArray.indices) {
                val checked = checkedSkillsArray[i]
                if (checked) {
                    tvMultiSelect.text = tvMultiSelect.text.toString() + colorsList[i] + "\n"
                }
            }
        }
        builder?.setNeutralButton("Cancel") { _, _ -> }
        builder?.create()?.show()
    }
    private fun configMultiSelectStudies(view: View) {
        val checkedStudiesArray = BooleanArray(studies.size)
        val tvMultiSelectStudies = view.findViewById<TextView>(R.id.tvMultiSelectStudies)
        val builder = context?.let { AlertDialog.Builder(it) }
        val studiesList = mutableListOf<String>()
        // String array for alert dialog multi choice items
        for (i in studies.indices) {
            studiesList.add(studies[i].name)
            for (j in existentStudies.indices) {
                if (studies[i].id == existentStudies[j].id) {
                    checkedStudiesArray[i] = true
                }
            }
        }
        val studiesArray: Array<String> = studiesList.toTypedArray()
        val colorsList = listOf(*studiesArray)
        builder?.setTitle("Select your studies")
        builder?.setMultiChoiceItems(studiesArray, checkedStudiesArray) { _, which, isChecked ->
            checkedStudiesArray[which] = isChecked
            val currentItem = colorsList[which]
            Toast.makeText(context, "$currentItem $isChecked", Toast.LENGTH_SHORT).show()
        }
        builder?.setPositiveButton("OK") { _, _ ->
            tvMultiSelectStudies.hint = "Select your studies..."
            tvMultiSelectStudies.text = ""
            for (i in checkedStudiesArray.indices) {
                val checked = checkedStudiesArray[i]
                if (checked) {
                    tvMultiSelectStudies.text =
                        tvMultiSelectStudies.text.toString() + colorsList[i] + "\n"
                }
            }
        }
        builder?.setNeutralButton("Cancel") { _, _ -> }
        builder?.create()?.show()
    }
    private fun configMultiSelectLanguages(view: View) {
        val checkedLanguagesArray = BooleanArray(languages.size)
        val tvMultiSelect = view.findViewById<TextView>(R.id.tvMultiSelectLenguages)
        val builder = context?.let { AlertDialog.Builder(it) }
        val languagesList = mutableListOf<String>()
        // String array for alert dialog multi choice items
        for (i in languages.indices) {
            languagesList.add(languages[i].name)
            for (j in existentLanguages.indices) {
                if (languages[i].id == existentLanguages[j].id) {
                    checkedLanguagesArray[i] = true
                }
            }
        }
        val languagesArray: Array<String> = languagesList.toTypedArray()
        val colorsList = listOf(*languagesArray)
        builder?.setTitle("Select your languages")
        builder?.setMultiChoiceItems(languagesArray, checkedLanguagesArray) { _, which, isChecked ->
            checkedLanguagesArray[which] = isChecked
            val currentItem = colorsList[which]
            Toast.makeText(context, "$currentItem $isChecked", Toast.LENGTH_SHORT).show()
        }
        builder?.setPositiveButton("OK") { _, _ ->
            tvMultiSelect.hint = "Select your languages..."
            tvMultiSelect.text = ""
            for (i in checkedLanguagesArray.indices) {
                val checked = checkedLanguagesArray[i]
                if (checked) {
                    tvMultiSelect.text = tvMultiSelect.text.toString() + colorsList[i] + "\n"
                }
            }
        }
        builder?.setNeutralButton("Cancel") { _, _ -> }
        builder?.create()?.show()
    }
    private fun saveEditedProfProfile(view: View) {

        val sharedPreferences = context?.let { SharedPreferences(it) }
        val id = sharedPreferences?.getValues("id").toString().toInt()
        val etDegreeEdit = view.findViewById<EditText>(R.id.etDegreeEdit)
        val etProfileDescriptionShow = view.findViewById<EditText>(R.id.etProfileDescriptionShow)

        val profProfileBri = ProfProfile(professionalProfilePostulant.id, etDegreeEdit.text.toString(), etProfileDescriptionShow.text.toString(), professionalProfilePostulant.video)

        profileId.let {
            PostulantService.postulantInstance.editProfileOfSpecificPostulant(
                id,
                profileId, profProfileBri
            )
        }.enqueue(object : Callback<ProfProfile> {
            override fun onResponse(call: Call<ProfProfile>, response: Response<ProfProfile>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Successfully updated", Toast.LENGTH_LONG).show()
                    Handler().postDelayed({
                        val intent = Intent(context, NavigationPostulantActivity::class.java)
                        startActivity(intent)
                    }, 1700)
                }
            }

            override fun onFailure(call: Call<ProfProfile>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Operation unsuccessfully",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
    private fun getData(profileId: Int) {
        val request = PostulantService.postulantInstance.getAllLanguages()
        val request1 = PostulantService.postulantInstance.getAllStudies()
        val request2 = PostulantService.postulantInstance.getAllSkills()
        val request3 = PostulantService.postulantInstance.getSkillsByProfileId(profileId)
        val request4 = PostulantService.postulantInstance.getLanguagesByProfileId(profileId)
        val request5 = PostulantService.postulantInstance.getStudiesByProfileId(profileId)

        request.enqueue(object : Callback<Languages> {
            override fun onResponse(call: Call<Languages>, response: Response<Languages>) {
                if (response.isSuccessful){
                    val content = response.body()
                    if (content != null) {
                        languages = content.languages
                        Log.d("Brigitte", languages.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Languages>, t: Throwable) {
                Toast.makeText(context, "Languages could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })

        request1.enqueue(object : Callback<Studies> {
            override fun onResponse(call: Call<Studies>, response: Response<Studies>) {
                if (response.isSuccessful){
                    val content = response.body()
                    if (content != null) {
                        studies = content.studies
                        Log.d("Brigitte", studies.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Studies>, t: Throwable) {
                Toast.makeText(context, "Studies could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })

        request2.enqueue(object : Callback<Skills> {
            override fun onResponse(call: Call<Skills>, response: Response<Skills>) {
                if (response.isSuccessful){
                    val content = response.body()
                    if (content != null) {
                        skills = content.skills
                        Log.d("Brigitte", skills.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Skills>, t: Throwable) {
                Toast.makeText(context, "Skills could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })

        request3.enqueue(object : Callback<Skills> {
            override fun onResponse(call: Call<Skills>, response: Response<Skills>) {
                if (response.isSuccessful){
                    val content = response.body()
                    if (content != null) {
                        existentSkills = content.skills
                        Log.d("Brigitte", existentSkills.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Skills>, t: Throwable) {
                Toast.makeText(context, "Skills could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })

        request4.enqueue(object : Callback<Languages> {
            override fun onResponse(call: Call<Languages>, response: Response<Languages>) {
                if (response.isSuccessful){
                    val content = response.body()
                    if (content != null) {
                        existentLanguages = content.languages
                        Log.d("Brigitte", existentLanguages.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Languages>, t: Throwable) {
                Toast.makeText(context, "Languages could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })

        request5.enqueue(object : Callback<Studies> {
            override fun onResponse(call: Call<Studies>, response: Response<Studies>) {
                if (response.isSuccessful){
                    val content = response.body()
                    if (content != null) {
                        existentStudies = content.studies
                        Log.d("Brigitte", existentStudies.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Studies>, t: Throwable) {
                Toast.makeText(context, "Studies could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })

    }

}