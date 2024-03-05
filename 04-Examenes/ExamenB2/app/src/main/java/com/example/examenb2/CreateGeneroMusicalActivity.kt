package com.example.examenb2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examenb2.database.FirestoreGeneroMusical
import com.example.examenb2.models.GeneroMusical
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date


class CreateGeneroMusicalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_genero_musical)

        val btnCrearGeneroMusical = findViewById<Button>(R.id.btn_actualizar_genero)

        btnCrearGeneroMusical.setOnClickListener {
            crearGeneroMusical()
            irActividad(MainActivity::class.java)
        }

        val btnBackC = findViewById<Button>(R.id.btn_back_create_g)
        btnBackC.setOnClickListener {
            irActividad(MainActivity::class.java)
        }


    }


    fun crearGeneroMusical(){
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val descripcion = findViewById<EditText>(
            R.id.input_descripcion
        )

        val fechaCreacion = findViewById<EditText>(
            R.id.input_fecha
        )

        val popularidad = findViewById<EditText>(
            R.id.input_popularidad
        )

        FirestoreGeneroMusical.createGeneroMusical(
            GeneroMusical(
                "",
                nombre.text.toString(),
                SimpleDateFormat("dd/MM/yyyy").parse(fechaCreacion.text.toString()),
                descripcion.text.toString(),
                popularidad.text.toString().toInt()
            )
        ) {
            if (it) {
                mostrarSnackbar("Se ha creado el género musical")
                irActividad(
                    MainActivity::class.java
                )
            } else {
                mostrarSnackbar("No se ha podido crear el género musical")
            }
        }


    }

    fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(R.id.form_create_genero), // view
            texto, // texto
            Snackbar.LENGTH_LONG // tiempo
        ).show()
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}