package com.example.paradox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class FiltrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros)

        val spinner_fecha_de_publicacion: Spinner = findViewById(R.id.spFechaDePublicacion)
        ArrayAdapter.createFromResource(
            this,
            R.array.fecha_de_publicacion_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_fecha_de_publicacion.adapter = adapter
        }
        val spinner_tipo_de_contrato: Spinner = findViewById(R.id.spTipoDeContrato)
        ArrayAdapter.createFromResource(
            this,
            R.array.tipo_de_contrato_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_tipo_de_contrato.adapter = adapter
        }
        val spinner_salario: Spinner = findViewById(R.id.spSalario)
        ArrayAdapter.createFromResource(
            this,
            R.array.salario_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_salario.adapter = adapter
        }
        val spinner_lugar: Spinner = findViewById(R.id.spLugar)
        ArrayAdapter.createFromResource(
            this,
            R.array.lugar_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_lugar.adapter = adapter
        }
    }
}