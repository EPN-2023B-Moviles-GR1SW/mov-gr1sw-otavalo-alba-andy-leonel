package com.example.examenb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.examenb1.database.CrudCancion
import com.example.examenb1.database.DBSQLite
import com.example.examenb1.models.Cancion
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

class EditCancionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cancion)

        val idCancion = intent.getIntExtra("idCancion", 0)

        val cancion = DBSQLite.tablaCancion!!.consultarCancionPorId(idCancion)

        llenarInputs(cancion!!)

        val btnActualizar = findViewById<Button>(R.id.btn_actualizar_cancion)
        btnActualizar.setOnClickListener {
            actualizarCancion()
            mostrarSnackbar("Cancion actualizada")

            val intent = Intent(this, VerCancionesActivity::class.java)
            intent.putExtra("id", cancion.generoId.toString())
            startActivity(intent)
        }

        val btnBack = findViewById<Button>(R.id.btn_back_edit_c)
        btnBack.setOnClickListener {
            val intent = Intent(this, VerCancionesActivity::class.java)
            intent.putExtra("id", cancion.generoId.toString())
            startActivity(intent)
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

    fun llenarInputs(cancion: Cancion) {
        val id = findViewById<EditText>(R.id.et_id_c)
        val nombre = findViewById<EditText>(R.id.et_nombre_c)
        val duracion = findViewById<EditText>(R.id.et_duracion_c)
        val fechaLanzamiento = findViewById<EditText>(R.id.et_fecha_c)
        val esColaborativa = findViewById<CheckBox>(R.id.chk_colaborativa)

        id.setText(cancion.id.toString())
        nombre.setText(cancion.nombre)
        duracion.setText(cancion.duracion.toString())
        fechaLanzamiento.setText(SimpleDateFormat("dd/MM/yyyy").format(cancion.fechaLanzamiento))
        esColaborativa.isChecked = cancion.esColaborativa
    }

    fun actualizarCancion() {
        val id = findViewById<EditText>(R.id.et_id_c)
        val nombre = findViewById<EditText>(R.id.et_nombre_c)
        val duracion = findViewById<EditText>(R.id.et_duracion_c)
        val fechaLanzamiento = findViewById<EditText>(R.id.et_fecha_c)
        val esColaborativa = findViewById<CheckBox>(R.id.chk_colaborativa)
        val idCancion = intent.getIntExtra("idCancion", 0)
        val cancion = DBSQLite.tablaCancion!!.consultarCancionPorId(idCancion)
        val idgenero = cancion!!.generoId




        CrudCancion().editarCancion(
            id.text.toString().toInt(),
            nombre.text.toString(),
            duracion.text.toString().toInt(),
            idgenero,
            esColaborativa.isChecked,
            fechaLanzamiento.text.toString(),
        )
    }

}