package com.example.examenb2

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.example.examenb2.database.FirestoreGeneroMusical
import com.example.examenb2.models.GeneroMusical
import com.google.android.material.snackbar.Snackbar


class MainActivity : ComponentActivity() {

    private var posicionItemSeleccionado = 0
    private lateinit var arregloGeneros: ArrayList<GeneroMusical>
    private lateinit var adaptador: ArrayAdapter<GeneroMusical>
    private lateinit var listViewGeneros: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arregloGeneros = ArrayList()
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloGeneros)
        listViewGeneros = findViewById(R.id.lv_list_genero_musical)
        listViewGeneros.adapter = adaptador

        obtenerGenerosMusicales()
        registerForContextMenu(listViewGeneros)
        val btnCrearGenero = findViewById<Button>(R.id.btn_ir_crear_genero)
        btnCrearGenero.setOnClickListener {
            irActividad(CreateGeneroMusicalActivity::class.java)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                val selectedItem = arregloGeneros[posicionItemSeleccionado]
                val intent = Intent(this, EditGeneroMusicalActivity::class.java)
                intent.putExtra("idGenero", selectedItem.id)
                startActivity(intent)
                return true
            }

            R.id.mi_eliminar -> {
                val selectedItem = arregloGeneros[posicionItemSeleccionado]
                abrirDialogo(selectedItem.id)
                return true
            }

            R.id.mi_ver_canciones -> {
                val selectedItem = arregloGeneros[posicionItemSeleccionado]
                val intent = Intent(this, VerCancionesActivity::class.java)
                intent.putExtra("idGenero", selectedItem.id)
                startActivity(intent)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(id: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                eliminarGeneroMusical(id)
                mostrarSnackbar("Genero Musical eliminado")
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }


    fun eliminarGeneroMusical(id: String) {
        FirestoreGeneroMusical.deleteGeneroMusical(id) {
            obtenerGenerosMusicales()
        }

        FirestoreGeneroMusical.deleteCancionesByGeneroMusical(id) {
            obtenerGenerosMusicales()
        }
    }
    private fun obtenerGenerosMusicales() {
        FirestoreGeneroMusical.getAllGenerosMusicales { generos ->
            actualizarListView(generos)
        }
    }

    private fun actualizarListView(nuevosGeneros: ArrayList<GeneroMusical>) {
        arregloGeneros.clear()
        arregloGeneros.addAll(nuevosGeneros)
        adaptador.notifyDataSetChanged()
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_layout_main), // view
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
