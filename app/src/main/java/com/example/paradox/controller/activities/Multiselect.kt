package com.example.paradox.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.paradox.R
import org.w3c.dom.Text
import java.util.*
import java.util.Arrays.asList

class Multiselect : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiselect)
        val tvMultiSelect = findViewById<TextView>(R.id.tvMultiSelect)
        val checkedColorsArray = BooleanArray(6)

        tvMultiSelect.setOnClickListener {
            val builder = AlertDialog.Builder(this@Multiselect)
            // String array for alert dialog multi choice items
            val colorsArray = arrayOf("Black", "Orange", "Green", "Yellow", "White", "Purple")
            // Boolean array for initial selected items
            // Convert the color array to list
            val colorsList = Arrays.asList(*colorsArray)
            //setTitle
            builder.setTitle("Select colors")
            //set multichoice
            builder.setMultiChoiceItems(colorsArray, checkedColorsArray) { dialog, which, isChecked ->
                // Update the current focused item's checked status
                checkedColorsArray[which] = isChecked
                // Get the current focused item
                val currentItem = colorsList[which]
                // Notify the current action
                Toast.makeText(applicationContext, currentItem + " " + isChecked, Toast.LENGTH_SHORT).show()
            }
            // Set the positive/yes button click listener
            builder.setPositiveButton("OK") { dialog, which ->
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
            builder.setNeutralButton("Cancel") { dialog, which ->
                // Do something when click the neutral button
            }
            val dialog = builder.create()
            // Display the alert dialog on interface
            dialog.show()
        }
    }
}