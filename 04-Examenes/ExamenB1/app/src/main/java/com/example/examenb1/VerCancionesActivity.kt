package com.example.examenb1


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.examenb1.database.DBMemoria
import com.google.android.material.snackbar.Snackbar
import java.lang.Integer.parseInt

class VerCancionesActivity : AppCompatActivity() {

    val arregloCanciones = DBMemoria.arregloCancion
    var posicionItemSeleccionado = -1
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
                mostrarSnackbar("${posicionItemSeleccionado}")
                return true
            }

            R.id.mi_eliminar -> {
                mostrarSnackbar("${posicionItemSeleccionado}")
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_canciones)
        val idGenero = intent.getStringExtra("id")
        mostrarSnackbar("$idGenero")

        val arregloCancionesPorGenero = arregloCanciones.filter { cancion -> cancion.genero.id == parseInt(idGenero) }

        val listView = findViewById<ListView>(R.id.lv_list_canciones)
        val adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            arregloCancionesPorGenero
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)
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
}


