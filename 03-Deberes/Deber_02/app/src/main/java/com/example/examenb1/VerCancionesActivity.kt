package com.example.examenb1


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.example.examenb1.database.DBMemoria
import com.example.examenb1.models.Cancion
import com.google.android.material.snackbar.Snackbar
import java.lang.Integer.parseInt

class VerCancionesActivity : AppCompatActivity() {

    val arregloCanciones = DBMemoria.arregloCancion
    var posicionItemSeleccionado = -1
    var arregloCancionesPorGenero = arrayListOf<Cancion>()
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
                intent.putExtra("idCancion", arregloCancionesPorGenero[posicionItemSeleccionado].id)
                startActivity(intent)
                return true
            }

            R.id.mi_eliminar -> {
                abrirDialogo(posicionItemSeleccionado)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_canciones)
        val idGenero = intent.getStringExtra("id")
        mostrarSnackbar("Ver canciones de: $idGenero")

        arregloCancionesPorGenero =
            arregloCanciones.filter { cancion -> cancion.genero.id == parseInt(idGenero) } as ArrayList<Cancion>

        val listView = findViewById<ListView>(R.id.lv_list_canciones)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloCancionesPorGenero
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        val btnCrearCancion = findViewById<Button>(R.id.btn_ir_crear_cancion)
        btnCrearCancion.setOnClickListener {
            // Crear un Intent para iniciar CrearCancionActivity
            val intentCreate = Intent(this, CreateCancionActivity::class.java)

            // Pasar el parámetro "nombre" al Intent
            intentCreate.putExtra("idGenero", idGenero)
            // Iniciar la actividad VerCancionesActivity
            startActivity(intentCreate)
        }

        val btnBack = findViewById<Button>(R.id.btn_back_ver_c)
        btnBack.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
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


    fun eliminarCancion(id: Int) {

        val listView = findViewById<ListView>(R.id.lv_list_canciones)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloCancionesPorGenero
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val idCancionAEliminar = arregloCancionesPorGenero[id].id
        arregloCancionesPorGenero.removeAt(id)

        DBMemoria.arregloCancion.removeIf { cancion -> cancion.id == idCancionAEliminar }
    }

    fun abrirDialogo(id: Int) {
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


