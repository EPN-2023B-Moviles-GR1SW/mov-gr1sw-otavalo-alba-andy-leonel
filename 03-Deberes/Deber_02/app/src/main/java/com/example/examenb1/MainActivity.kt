package com.example.examenb1


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
import com.example.examenb1.database.CrudGeneroMusical
import com.example.examenb1.database.DBMemoria
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    val arregloGeneroMusical = DBMemoria.arregloGeneroMusical
    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                val selectedItem = arregloGeneroMusical[posicionItemSeleccionado]
                // Crear un Intent para iniciar VerCancionesActivity
                val intent = Intent(this, EditGeneroMusicalActivity::class.java)

                // Pasar el parámetro "nombre" al Intent
                intent.putExtra("id", selectedItem.id.toString())

                // Iniciar la actividad VerCancionesActivity
                startActivity(intent)
                return true
            }

            R.id.mi_eliminar -> {
                abrirDialogo(posicionItemSeleccionado)
                return true
            }

            R.id.mi_ver_canciones -> {
                val selectedItem = arregloGeneroMusical[posicionItemSeleccionado]
                // Crear un Intent para iniciar VerCancionesActivity
                val intent = Intent(this, VerCancionesActivity::class.java)

                // Pasar el parámetro "nombre" al Intent
                intent.putExtra("id", selectedItem.id.toString())

                // Iniciar la actividad VerCancionesActivity
                startActivity(intent)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.lv_list_genero_musical)
        val adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            arregloGeneroMusical
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)

        val btnCrearGeneroMusical = findViewById<View>(R.id.btn_ir_crear_genero)
        btnCrearGeneroMusical.setOnClickListener {
            irActividad(CreateGeneroMusicalActivity::class.java)
        }


    } // onCreate() <--


    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }


    fun eliminarGeneroMusical(id: Int) {
        val listView = findViewById<ListView>(R.id.lv_list_genero_musical)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloGeneroMusical
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        var idGeneroAEliminar = arregloGeneroMusical[id].id
        CrudGeneroMusical().eliminarCancionesDelGenero(idGeneroAEliminar)
        arregloGeneroMusical.removeAt(
            id
        )
    }

    fun abrirDialogo(id: Int) {
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

}




