package com.example.examenb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examenb1.database.CrudGeneroMusical
import com.example.examenb1.database.DBSQLite
import com.example.examenb1.database.ESqliteHelperGeneroMusical
import com.example.examenb1.models.GeneroMusical
import com.google.android.material.snackbar.Snackbar
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat

class EditGeneroMusicalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_genero_musical)
        DBSQLite.tablaGeneroMusical = ESqliteHelperGeneroMusical(this)
        val idGenero = intent.getStringExtra("id")
        val genero = DBSQLite.tablaGeneroMusical!!.consultarGeneroMusicalPorId(idGenero!!)
        llenarInputs(genero!!)
//        mostrarSnackbar("$idGenero")


        val btnActualizar = findViewById<Button>(R.id.btn_actualizar_genero)
        btnActualizar.setOnClickListener {
            actualizarGenero()
            mostrarSnackbar("Genero actualizado")
            irActividad(MainActivity::class.java)

        }

        val btnBack = findViewById<Button>(R.id.btn_back_edit_g)
        btnBack.setOnClickListener {
            irActividad(MainActivity::class.java)
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

    fun llenarInputs(genero: GeneroMusical) {
        val id = findViewById<EditText>(R.id.et_id)
        val nombre = findViewById<EditText>(R.id.et_nombre)
        val descripcion = findViewById<EditText>(R.id.et_descripcion)
        val popularidad = findViewById<EditText>(R.id.et_popularidad)
        val fechaCreacion = findViewById<EditText>(R.id.et_fecha)

        id.setText(genero.id.toString())
        nombre.setText(genero.nombre)
        descripcion.setText(genero.descripcion)
        popularidad.setText(genero.popularidad.toString())
        fechaCreacion.setText(SimpleDateFormat("dd/MM/yyyy").format(genero.fechaCreacion))
    }

    fun actualizarGenero() {
        val id = findViewById<EditText>(R.id.et_id)
        val nombre = findViewById<EditText>(R.id.et_nombre)
        val descripcion = findViewById<EditText>(R.id.et_descripcion)
        val popularidad = findViewById<EditText>(R.id.et_popularidad)
        val fechaCreacion = findViewById<EditText>(R.id.et_fecha)

        CrudGeneroMusical().editarGeneroMusical(
            id.text.toString().toInt(),
            nombre.text.toString(),
           fechaCreacion.text.toString(),
            descripcion.text.toString(),
            popularidad.text.toString().toInt()
        )
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}