package com.example.paradox.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.adapter.SkillAdapter
import com.example.paradox.models.Language
import com.example.paradox.models.Languages
import com.example.paradox.models.ProfProfile
import com.example.paradox.models.Skills
import com.example.paradox.network.PostulantService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditProfessionalProfileP : AppCompatActivity() {
    var languages = listOf<Language>()
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
        val retrofit = Retrofit.Builder()
            .baseUrl("https://movilesback.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postulantService: PostulantService = retrofit.create(PostulantService::class.java)
        val request = postulantService.getAllLanguages()

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
                Toast.makeText(this@EditProfessionalProfileP, "Data could not be retrieved", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun configMultiSelectSkills() {
        val tvMultiSelect = findViewById<TextView>(R.id.tvMultiSelectB)
        val checkedColorsArray = BooleanArray(6)
        val builder = AlertDialog.Builder(this@EditProfessionalProfileP)
        // String array for alert dialog multi choice items
        val colorsArray = arrayOf("Black", "Orange", "Green", "Yellow", "White", "Purple")
        // Boolean array for initial selected items
        // Convert the color array to list
        val colorsList = listOf(*colorsArray)
        //setTitle
        builder.setTitle("Select colors")
        //set multi choice
        builder.setMultiChoiceItems(colorsArray, checkedColorsArray) { _, which, isChecked ->
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
            tvMultiSelect.hint = "Your preferred colors....."
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
        // Display the alert dialog on interface
        dialog.show()
    }
}