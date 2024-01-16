package com.example.examenb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examenb1.database.CrudGeneroMusical
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

class CreateGeneroMusicalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_genero_musical)

        val btnCrearGeneroMusical = findViewById<Button>(R.id.btn_actualizar_genero)

        btnCrearGeneroMusical.setOnClickListener {
            crearGeneroMusical()
            irActividad(MainActivity::class.java)
        }


    }


    fun crearGeneroMusical(){
        // Obtener componentes visuales
        val id = findViewById<EditText>(R.id.et_id)
        val nombre = findViewById<EditText>(R.id.et_nombre)
        val descripcion = findViewById<EditText>(
            R.id.et_descripcion
        )

        val fechaCreacion = findViewById<EditText>(
            R.id.et_fecha
        )

        val popularidad = findViewById<EditText>(
            R.id.et_popularidad
        )

        CrudGeneroMusical().crearGeneroMusical(
            id.text.toString().toInt(),
            nombre.text.toString(),
            SimpleDateFormat("dd/MM/yyyy").parse(fechaCreacion.text.toString()),
            descripcion.text.toString(),
            popularidad.text.toString().toInt()
        )
        mostrarSnackbar("Se ha creado el género musical ${nombre.text}")
        irActividad(
            MainActivity::class.java
        )
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