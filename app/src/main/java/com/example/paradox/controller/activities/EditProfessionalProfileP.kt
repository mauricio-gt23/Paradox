package com.example.paradox.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.paradox.NavigationPostulantActivity
import com.example.paradox.R
import com.example.paradox.models.*
import com.example.paradox.network.PostulantService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfessionalProfileP : AppCompatActivity() {
    var languages = listOf<Language>()
    var existentLanguages = listOf<Language>()
    var studies = listOf<Study>()
    var existentStudies = listOf<Study>()
    var skills = listOf<Skill>()
    var existentSkills = listOf<Skill>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_professional_profile_p)
        val tvMultiSelect = findViewById<TextView>(R.id.tvMultiSelectB)
        val tvMultiSelectStudies = findViewById<TextView>(R.id.tvMultiSelectStudies)
        val tvMultiSelectLenguages = findViewById<TextView>(R.id.tvMultiSelectLenguages)
        val btSaveProfProfile = findViewById<TextView>(R.id.btSaveProfProfile)

        tvMultiSelect.setOnClickListener{
            configMultiSelectSkills()
        }
        tvMultiSelectStudies.setOnClickListener{
            configMultiSelectStudies()
        }
        tvMultiSelectLenguages.setOnClickListener{
            configMultiSelectLanguages()
        }
        btSaveProfProfile.setOnClickListener{
            saveEditedProfProfile()
        }
        if (intent.extras != null) {
            loadData()
        }
    }

    private fun loadData() {
        loadLocalData()
    }
    private fun loadLocalData() {
        val profProfile: ProfProfile = intent.getParcelableExtra("ProfProfile")!!
        val profileId: Int? = intent.extras?.getInt("profileId")
        val etDegreeEdit = findViewById<EditText>(R.id.etDegreeEdit)
        val etProfileDescriptionShow = findViewById<EditText>(R.id.etProfileDescriptionShow)
        etDegreeEdit.setText(profProfile.ocupation)
        etProfileDescriptionShow.setText(profProfile.description)
        if (profileId != null) {
            getData(profileId)
        }
    }

    private fun saveEditedProfProfile() {

        val sharedPreferences = SharedPreferences(this@EditProfessionalProfileP)
        val id = sharedPreferences.getValues("id").toString().toInt()
        val profileId: Int? = intent.extras?.getInt("profileId")
        val etDegreeEdit = findViewById<EditText>(R.id.etDegreeEdit)
        val etProfileDescriptionShow = findViewById<EditText>(R.id.etProfileDescriptionShow)
        val profProfileLocal: ProfProfile = intent.getParcelableExtra("ProfProfile")!!

        val profProfileBri = ProfProfile(profProfileLocal.id, etDegreeEdit.text.toString(), etProfileDescriptionShow.text.toString(), profProfileLocal.video)

        profileId?.let {
            PostulantService.postulantInstance.editProfileOfSpecificPostulant(
                id,
                profileId, profProfileBri
            )
        }?.enqueue(object : Callback<ProfProfile> {
            override fun onResponse(call: Call<ProfProfile>, response: Response<ProfProfile>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditProfessionalProfileP, "Successfully updated", Toast.LENGTH_LONG).show()
                    Handler().postDelayed({
                        val intent = Intent(this@EditProfessionalProfileP, NavigationPostulantActivity::class.java)
                        startActivity(intent)
                    }, 1700)
                }
            }

            override fun onFailure(call: Call<ProfProfile>, t: Throwable) {
                Toast.makeText(
                    this@EditProfessionalProfileP,
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
                Toast.makeText(this@EditProfessionalProfileP, "Languages could not be retrieved", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@EditProfessionalProfileP, "Studies could not be retrieved", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@EditProfessionalProfileP, "Languages could not be retrieved", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@EditProfessionalProfileP, "Studies could not be retrieved", Toast.LENGTH_LONG).show()
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
    private fun configMultiSelectStudies() {
        val checkedStudiesArray = BooleanArray(studies.size)
        val tvMultiSelectStudies = findViewById<TextView>(R.id.tvMultiSelectStudies)
        val builder = AlertDialog.Builder(this@EditProfessionalProfileP)
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
        builder.setTitle("Select your studies")
        builder.setMultiChoiceItems(studiesArray, checkedStudiesArray) { _, which, isChecked ->
            checkedStudiesArray[which] = isChecked
            val currentItem = colorsList[which]
            Toast.makeText(applicationContext, "$currentItem $isChecked", Toast.LENGTH_SHORT).show()
        }
        builder.setPositiveButton("OK") { _, _ ->
            tvMultiSelectStudies.hint = "Select your studies..."
            tvMultiSelectStudies.text =           ""
            for (i in checkedStudiesArray.indices) {
                val checked = checkedStudiesArray[i]
                if (checked) {
                    tvMultiSelectStudies.text = tvMultiSelectStudies.text.toString() + colorsList[i] + "\n"
                }
            }
        }
        builder.setNeutralButton("Cancel") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }
    private fun configMultiSelectLanguages() {
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