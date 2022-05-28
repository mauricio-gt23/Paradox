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
    private val languagesList = mutableListOf<String>()

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
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val spinner2: Spinner = findViewById(R.id.spSkills)
        ArrayAdapter.createFromResource(
            this,
            R.array.skills_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
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
    }

    private fun configMultiSelectSkills() {
        val checkedColorsArray = BooleanArray(skills.size)
        val tvMultiSelect = findViewById<TextView>(R.id.tvMultiSelectB)
        val builder = AlertDialog.Builder(this@EditProfessionalProfileP)
        // String array for alert dialog multi choice items
        val colorsArray = arrayOf("Black", "Orange", "Green", "Yellow", "White", "Purple")
        for (i in skills.indices) {
            languagesList.add(skills[i].name)
        }
        val languagesArray: Array<String> = languagesList.toTypedArray()
        // Boolean array for initial selected items
        // Convert the color array to list
        val colorsList = listOf(*languagesArray)
        builder.setTitle("Select your skills")
        //set multi choice
        builder.setMultiChoiceItems(languagesArray, checkedColorsArray) { _, which, isChecked ->
            // Update the current focused item's checked status
            checkedColorsArray[which] = isChecked
            // Get the current focused item
            val currentItem = colorsList[which]
            // Notify the current action
            Toast.makeText(applicationContext, "$currentItem $isChecked", Toast.LENGTH_SHORT).show()
        }
        // Set the positive/yes button click listener
        builder.setPositiveButton("OK") { _, _ ->
            // Do something when click positive button
            tvMultiSelect.hint = "Select your skills..."
            tvMultiSelect.text = ""
            for (i in checkedColorsArray.indices) {
                val checked = checkedColorsArray[i]
                if (checked) {
                    tvMultiSelect.text = tvMultiSelect.text.toString() + colorsList[i] + "\n"
                }
            }
        }
        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel") { _, _ ->
            // Do something when click the neutral button
        }
        val dialog = builder.create()
        dialog.show()
    }
}