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


class EditGeneroMusicalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_genero_musical)
        val idGenero = intent.getStringExtra("idGenero")
        if (idGenero != null) {
            obtenerGeneroMusicalPorId(idGenero)
            val btnActualizar = findViewById<Button>(R.id.btn_actualizar_genero)
            btnActualizar.setOnClickListener {
                actualizarGeneroMusical()
            }
        }else{
            mostrarSnackbar("No se ha encontrado el id del genero")
            irActividad(MainActivity::class.java)
        }
    }

    fun obtenerGeneroMusicalPorId(idGenero: String) {
        FirestoreGeneroMusical.getGeneroMusicalById(idGenero) {
            if (it != null) {
//                Llenar los campos del formulario
                val id = findViewById<EditText>(R.id.et_id)
                val nombre = findViewById<EditText>(R.id.et_nombre)
                val descripcion = findViewById<EditText>(R.id.et_descripcion)
                val popularidad = findViewById<EditText>(R.id.et_popularidad)
                val fechaCreacion = findViewById<EditText>(R.id.et_fecha)

                id.setText(it.id)
                nombre.setText(it.nombre)
                descripcion.setText(it.descripcion)
                popularidad.setText(it.popularidad.toString())
                fechaCreacion.setText(SimpleDateFormat("dd/MM/yyyy").format(it.fechaCreacion))

                mostrarSnackbar("Genero musical encontrado")
            } else {
                mostrarSnackbar("Genero musical no encontrado")
            }
        }
    }

    fun actualizarGeneroMusical() {
        val id = findViewById<EditText>(R.id.et_id).text.toString()
        val nombre = findViewById<EditText>(R.id.et_nombre).text.toString()
        val descripcion = findViewById<EditText>(R.id.et_descripcion).text.toString()
        val popularidad = findViewById<EditText>(R.id.et_popularidad).text.toString().toInt()
        val fechaCreacion = findViewById<EditText>(R.id.et_fecha).text.toString()

        val genero = GeneroMusical(
            id,
            nombre,
            SimpleDateFormat("dd/MM/yyyy").parse(fechaCreacion),
            descripcion,
            popularidad
        )

        FirestoreGeneroMusical.updateGeneroMusical(genero) {
            if (it) {
                mostrarSnackbar("Genero musical actualizado")
                irActividad(MainActivity::class.java)
            } else {
                mostrarSnackbar("Error al actualizar el genero musical")
            }
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.form_edit_genero), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }



    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}