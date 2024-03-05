package com.example.examenb2

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.examenb2.database.FirestoreCancion
import com.example.examenb2.models.Cancion
import com.example.examenb2.models.GeneroMusical
import com.google.android.material.snackbar.Snackbar

class VerCancionesActivity : AppCompatActivity() {

    private var posicionItemSeleccionado = 0
    private lateinit var arregloCanciones: ArrayList<Cancion>
    private lateinit var listViewCanciones: ListView
    private lateinit var adaptador: ArrayAdapter<Cancion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_canciones)

        val idGenero = intent.getStringExtra("idGenero")

        if (idGenero != null) {
            arregloCanciones = ArrayList()
            listViewCanciones = findViewById(R.id.lv_list_canciones)
            adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloCanciones)
            listViewCanciones.adapter = adaptador
            obtenerCancionesDesdeFirestore(idGenero)
            registerForContextMenu(listViewCanciones)

            val btn_crear_cancion = findViewById<View>(R.id.btn_ir_crear_cancion)
            btn_crear_cancion.setOnClickListener {
                val intent = Intent(this, CreateCancionActivity::class.java)
                intent.putExtra("idGenero", idGenero)
                startActivity(intent)
            }

            val btn_ir_generos = findViewById<View>(R.id.btn_back_ver_c)
            btn_ir_generos.setOnClickListener {
                irActividad(MainActivity::class.java)
            }

        } else {
            mostrarSnackbar("No se ha encontrado el id del genero")
            irActividad(MainActivity::class.java)
        }
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_cancion, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                val intent = Intent(this, EditCancionActivity::class.java)
                intent.putExtra("idCancion", arregloCanciones[posicionItemSeleccionado].id)
                startActivity(intent)
                return true
            }

            R.id.mi_eliminar -> {
                var idCancion = arregloCanciones[posicionItemSeleccionado].id
                abrirDialogo(idCancion)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun obtenerCancionesDesdeFirestore(idGenero:String) {
        FirestoreCancion.getCancionesByGeneroMusical(idGenero) { canciones ->
            actualizarListaCanciones(canciones)
        }
    }

    private fun actualizarListaCanciones(nuevasCanciones: ArrayList<Cancion>) {
        adaptador.clear()
        adaptador.addAll(nuevasCanciones)
        adaptador.notifyDataSetChanged()
    }
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_layout_intents), // view
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


    fun eliminarCancion(id: String) {
        FirestoreCancion.deleteCancion(id) {
            if (it) {
                val idGenero = intent.getStringExtra("idGenero")
                if (idGenero != null) {
                    obtenerCancionesDesdeFirestore(idGenero)
                }
            } else {
                mostrarSnackbar("Error al eliminar la canción")
            }
        }
    }

    fun abrirDialogo(id: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                eliminarCancion(id)
                mostrarSnackbar("Canción eliminada")
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }
}
