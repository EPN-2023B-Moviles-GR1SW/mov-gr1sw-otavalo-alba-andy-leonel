package com.example.examenb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import android.widget.EditText
import com.example.examenb1.database.CrudCancion
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat

class CreateCancionActivity : AppCompatActivity() {

    var idGenero = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cancion)

        idGenero = intent.getStringExtra("idGenero").toString()
        mostrarSnackbar("$idGenero")

        val btnCrearCancion = findViewById<Button>(R.id.btn_crear_cancion)
        btnCrearCancion.setOnClickListener {

            crearCancion()

        }

        val btnBack = findViewById<Button>(R.id.btn_back_create_c)
        btnBack.setOnClickListener {
            // Crear un Intent para iniciar Ver Canciones
            val intent = Intent(this, VerCancionesActivity::class.java)

            // Pasar el parámetro "nombre" al Intent
            intent.putExtra("id", idGenero)
            // Iniciar la actividad VerCancionesActivity
            startActivity(intent)
        }

    }


    fun crearCancion() {

        val id = findViewById<EditText>(R.id.input_id_c)
        val nombre = findViewById<EditText>(R.id.input_nombre_c)
        val duracion = findViewById<EditText>(R.id.input_duracion_c)
        val idgenero = parseInt(idGenero)
        val esColaborativa = findViewById<CheckBox>(R.id.chk_colaborativa).isChecked
        val fechaLanzamiento = findViewById<EditText>(R.id.input_fecha_c)

        CrudCancion().crearCancion(
            id.text.toString().toInt(),
            nombre.text.toString(),
            SimpleDateFormat("dd/MM/yyyy").parse(fechaLanzamiento.text.toString()),
            duracion.text.toString().toInt(),
            idgenero,
            esColaborativa,
        )

        mostrarSnackbar("Se ha creado la canción ${nombre.text} y el id genero es $idgenero")


        // Crear un Intent para iniciar Ver Canciones
        val intent = Intent(this, VerCancionesActivity::class.java)

        // Pasar el parámetro "nombre" al Intent
        intent.putExtra("id", idGenero)
        // Iniciar la actividad VerCancionesActivity
        startActivity(intent)
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