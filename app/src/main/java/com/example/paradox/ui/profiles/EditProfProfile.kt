package com.example.paradox.ui.profiles

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.example.paradox.models.Language
import com.example.paradox.models.ProfProfile
import com.example.paradox.models.Skill
import com.example.paradox.models.Study
import com.example.paradox.network.PostulantService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditProfProfile : Fragment() {
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
            professionalProfilePostulant = it.getParcelable("companyId")!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val vista: View = inflater.inflate(R.layout.fragment_edit_prof_profile, container, false)
        val tvMultiSelect = vista.findViewById<TextView>(R.id.tvMultiSelectB)
        val tvMultiSelectStudies = vista.findViewById<TextView>(R.id.tvMultiSelectStudies)
        val tvMultiSelectLenguages = vista.findViewById<TextView>(R.id.tvMultiSelectLenguages)
        val btSaveProfProfile = vista.findViewById<TextView>(R.id.btSaveProfProfile)

        tvMultiSelect.setOnClickListener{
            configMultiSelectSkills(vista)
        }
        tvMultiSelectStudies.setOnClickListener{
            configMultiSelectStudies(vista)
        }
        tvMultiSelectLenguages.setOnClickListener{
            configMultiSelectLanguages(vista)
        }
        btSaveProfProfile.setOnClickListener{
            saveEditedProfProfile(vista)
        }
        return vista
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
    private fun saveEditedProfProfile(view: View) {

        val sharedPreferences = context?.let { SharedPreferences(it) }
        val id = sharedPreferences?.getValues("id").toString().toInt()
        val etDegreeEdit = view.findViewById<EditText>(R.id.etDegreeEdit)
        val etProfileDescriptionShow = view.findViewById<EditText>(R.id.etProfileDescriptionShow)

        val profProfileBri = ProfProfile(professionalProfilePostulant.id, etDegreeEdit.text.toString(), etProfileDescriptionShow.text.toString(), professionalProfilePostulant.video)

        profileId?.let {
            PostulantService.postulantInstance.editProfileOfSpecificPostulant(
                id,
                profileId, profProfileBri
            )
        }?.enqueue(object : Callback<ProfProfile> {
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
}