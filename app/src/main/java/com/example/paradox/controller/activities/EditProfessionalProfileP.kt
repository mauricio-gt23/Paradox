package com.example.paradox.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.paradox.R
import com.example.paradox.models.*
import com.example.paradox.network.PostulantService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfessionalProfileP : AppCompatActivity() {
    var languages = listOf<Language>()
    var skills = listOf<Skill>()
    var existentSkills = listOf<Skill>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_professional_profile_p)
        val spinner: Spinner = findViewById(R.id.spStudyDegree)
        val tvMultiSelect = findViewById<TextView>(R.id.tvMultiSelectB)

        ArrayAdapter.createFromResource(
            this,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        val spinner2: Spinner = findViewById(R.id.spSkills)
        ArrayAdapter.createFromResource(
            this,
            R.array.skills_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }
        tvMultiSelect.setOnClickListener{
            configMultiSelectSkills()
        }
        if (intent.extras != null) {
            loadData()
        }
    }

    private fun loadData() {
        loadLocalData()
        getData()
    }
    private fun loadLocalData() {
        val profProfile: ProfProfile = intent.getParcelableExtra("ProfProfile")!!
        val etDegreeEdit = findViewById<EditText>(R.id.etDegreeEdit)
        val etProfileDescriptionShow = findViewById<EditText>(R.id.etProfileDescriptionShow)
        etDegreeEdit.setText(profProfile.ocupation)
        etProfileDescriptionShow.setText(profProfile.description)
    }

    private fun getData() {
        val request = PostulantService.postulantInstance.getAllLanguages()
        val request2 = PostulantService.postulantInstance.getAllSkills()
        val request3 = PostulantService.postulantInstance.getSkillsByProfileId(1)

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
                Toast.makeText(this@EditProfessionalProfileP, "Languages could not be retrieved", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@EditProfessionalProfileP, "Skills could not be retrieved", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@EditProfessionalProfileP, "Skills could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun configMultiSelectSkills() {
        val checkedSkillsArray = BooleanArray(skills.size)
        val tvMultiSelect = findViewById<TextView>(R.id.tvMultiSelectB)
        val builder = AlertDialog.Builder(this@EditProfessionalProfileP)
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
        builder.setTitle("Select your skills")
        builder.setMultiChoiceItems(languagesArray, checkedSkillsArray) { _, which, isChecked ->
            checkedSkillsArray[which] = isChecked
            val currentItem = colorsList[which]
            Toast.makeText(applicationContext, "$currentItem $isChecked", Toast.LENGTH_SHORT).show()
        }
        builder.setPositiveButton("OK") { _, _ ->
            tvMultiSelect.hint = "Select your skills..."
            tvMultiSelect.text = ""
            for (i in checkedSkillsArray.indices) {
                val checked = checkedSkillsArray[i]
                if (checked) {
                    tvMultiSelect.text = tvMultiSelect.text.toString() + colorsList[i] + "\n"
                }
            }
        }
        builder.setNeutralButton("Cancel") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }
}