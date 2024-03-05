package com.example.examenb2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.examenb2.database.FirestoreCancion
import com.example.examenb2.models.Cancion

import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat


class EditCancionActivity : AppCompatActivity() {

    var idGenero = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cancion)

        val idCancion = intent.getStringExtra("idCancion")

        if (idCancion != null) {
            getCancionById(idCancion)

            val btnActualizar = findViewById<Button>(R.id.btn_actualizar_cancion)
            btnActualizar.setOnClickListener {
                actualizarCancion(idGenero)
            }
        } else {
            mostrarSnackbar("No se ha encontrado el id de la cancion")
            irActividad(MainActivity::class.java)
        }
    }


    fun getCancionById(idCancion: String) {
        FirestoreCancion.getCancionById(idCancion) {
            if (it != null) {
                val id = findViewById<EditText>(R.id.et_id_c)
                val nombre = findViewById<EditText>(R.id.et_nombre_c)
                val duracion = findViewById<EditText>(R.id.et_duracion_c)
                val fechaLanzamiento = findViewById<EditText>(R.id.et_fecha_c)
                val esColaborativa = findViewById<CheckBox>(R.id.chk_colaborativa)

                idGenero = it.idGenero

                id.setText(idCancion)
                nombre.setText(it.nombre)
                duracion.setText(it.duracion.toString())
                esColaborativa.isChecked = it.esColaborativa
                fechaLanzamiento.setText(SimpleDateFormat("dd/MM/yyyy").format(it.fechaLanzamiento))

                mostrarSnackbar("Cancion encontrada")
            } else {
                mostrarSnackbar("Cancion no encontrada")
            }
        }
    }

    fun actualizarCancion(idGenero: String) {
        val id = findViewById<EditText>(R.id.et_id_c).text.toString()
        val nombre = findViewById<EditText>(R.id.et_nombre_c).text.toString()
        val duracion = findViewById<EditText>(R.id.et_duracion_c).text.toString().toInt()
        val fechaLanzamiento = findViewById<EditText>(R.id.et_fecha_c).text.toString()
        val esColaborativa = findViewById<CheckBox>(R.id.chk_colaborativa).isChecked

        val cancion = Cancion(
            id,
            nombre,
            duracion,
            idGenero,
            esColaborativa,
            SimpleDateFormat("dd/MM/yyyy").parse(fechaLanzamiento)
        )

        FirestoreCancion.updateCancion(cancion) {
            if (it) {
                mostrarSnackbar("Cancion actualizada")
                val intent = Intent(this, VerCancionesActivity::class.java)
                intent.putExtra("idGenero", idGenero)
                startActivity(intent)
            } else {
                mostrarSnackbar("Error al actualizar la cancion")
            }
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.form_edit_cancion), // view
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