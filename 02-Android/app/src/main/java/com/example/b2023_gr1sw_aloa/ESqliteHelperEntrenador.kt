package com.example.b2023_gr1sw_aloa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto, "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(bd: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarEntrenadorFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("nombre", nombre)
        valoresActualizar.put("descripcion", descripcion)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR",
                valoresActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun consultarEntrenadorPorId(id: Int): BEntrenador {
        val baseDatosLectura = readableDatabase
        val scriptConsultarEntrenador = """
            SELECT * FROM ENTRENADOR WHERE id = ?
            """.trimIndent()

        val parametrosConsultaLectura = arrayOf(id.toString())

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarEntrenador,
            parametrosConsultaLectura
        )

        val existeEntrenador = resultadoConsultaLectura.moveToFirst()
        val entrenadorEncontrado = BEntrenador(0, "", "")
        if (existeEntrenador) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val descripcion =
                    resultadoConsultaLectura.getString(2) // Columna indice 2 -> DESCRIPCION
                if (id != null) {
                    entrenadorEncontrado.id = id
                    entrenadorEncontrado.nombre = nombre
                    entrenadorEncontrado.descripcion = descripcion
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return entrenadorEncontrado
    }


}