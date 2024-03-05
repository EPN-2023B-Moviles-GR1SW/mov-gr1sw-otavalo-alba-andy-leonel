package com.example.examenb2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.examenb2.database.FirestoreCancion
import com.example.examenb2.models.Cancion
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat


class CreateCancionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cancion)

        val idGenero = intent.getStringExtra("idGenero")

        if (idGenero != null) {

            val btnCrearCancion = findViewById<Button>(R.id.btn_crear_cancion)
            btnCrearCancion.setOnClickListener {
                crearCancion(idGenero)
            }

        } else {
            mostrarSnackbar("No se ha seleccionado un género")
        }
    }


    fun crearCancion(idGenero: String) {
        val nombre = findViewById<EditText>(R.id.input_nombre_c)
        val duracion = findViewById<EditText>(R.id.input_duracion_c)
        val esColaborativa = findViewById<CheckBox>(R.id.chk_colaborativa).isChecked
        val fechaLanzamiento = findViewById<EditText>(R.id.input_fecha_c)

        FirestoreCancion.createCancion(
            Cancion(
                "",
                nombre.text.toString(),
                duracion.text.toString().toInt(),
                idGenero,
                esColaborativa,
                SimpleDateFormat("dd/MM/yyyy").parse(fechaLanzamiento.text.toString())
            )
        ) {
            if (it) {
                mostrarSnackbar("Se ha creado la canción")
                val intent = Intent(this, VerCancionesActivity::class.java)
                intent.putExtra("idGenero", idGenero)
                startActivity(intent)
            } else {
                mostrarSnackbar("No se ha podido crear la canción")
            }
        }

    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.form_create_cancion), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
}