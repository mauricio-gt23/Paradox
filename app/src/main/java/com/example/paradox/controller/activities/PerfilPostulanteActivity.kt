package com.example.paradox.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.paradox.R

class PerfilPostulanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_postulante)
        val spinner_grado_estudios: Spinner = findViewById(R.id.spGradoDeEstudios)
        ArrayAdapter.createFromResource(
            this,
            R.array.grado_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_grado_estudios.adapter = adapter
        }

        val spinner_habilidades: Spinner = findViewById(R.id.spHabilidades)
        ArrayAdapter.createFromResource(
            this,
            R.array.habilidades_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_habilidades.adapter = adapter
        }
        val spinner_Idiomas: Spinner = findViewById(R.id.spIdiomas)
        ArrayAdapter.createFromResource(
            this,
            R.array.idiomas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_Idiomas.adapter = adapter
        }

    }
}