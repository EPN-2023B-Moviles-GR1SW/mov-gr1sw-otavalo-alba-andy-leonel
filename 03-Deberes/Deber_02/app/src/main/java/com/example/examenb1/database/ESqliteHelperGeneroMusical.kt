package com.example.examenb1.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examenb1.models.GeneroMusical
import java.text.SimpleDateFormat

class ESqliteHelperGeneroMusical(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto, "Deber02",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaGeneroMusical =
            """
                CREATE TABLE GeneroMusical (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(255) NOT NULL,
                fechaCreacion DATE NOT NULL,
                descripcion TEXT,
                popularidad INTEGER
                );
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaGeneroMusical)
    }

    override fun onUpgrade(bd: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    fun obtenerTodosGenerosMusicales():ArrayList<GeneroMusical>{

        var listGeneroMusical = ArrayList<GeneroMusical>()

        val baseDatosLectura = readableDatabase
        val scriptConsultarGeneroMusical = """
            SELECT * FROM GeneroMusical
            """.trimIndent()



        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarGeneroMusical,
            null
        )

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val GeneroMusicalEncontrado =
                    GeneroMusical(0, "", SimpleDateFormat("dd/MM/yyyy").parse("12/01/2023"), "", 0)

                val id = resultadoConsultaLectura.getInt(0) // Columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val fechaCreacion =
                    resultadoConsultaLectura.getString(3) // Columna indice 2 -> FECHA_CREACION
                val descripcion =
                    resultadoConsultaLectura.getString(2) // Columna indice 3 -> DESCRIPCION
                val popularidad =
                    resultadoConsultaLectura.getInt(4) // Columna indice 4 -> POPULARIDAD

                if (id != null) {
                    GeneroMusicalEncontrado.id = id
                    GeneroMusicalEncontrado.nombre = nombre
                    GeneroMusicalEncontrado.fechaCreacion =
                        SimpleDateFormat("dd/MM/yyyy").parse(fechaCreacion)
                    GeneroMusicalEncontrado.descripcion = descripcion
                    GeneroMusicalEncontrado.popularidad = popularidad

                    listGeneroMusical.add(GeneroMusicalEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return listGeneroMusical
    }

    fun crearGeneroMusical(
        nombre: String,
        fechaCreacion: String,
        descripcion: String,
        popularidad: Int
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaCreacion", fechaCreacion)
        valoresAGuardar.put("descripcion", descripcion)
        valoresAGuardar.put("popularidad", popularidad)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "GeneroMusical",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarGeneroMusicalFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "GeneroMusical",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun actualizarGeneroMusicalFormulario(
        nombre: String,
        fechaCreacion: String,
        descripcion: String,
        popularidad: Int,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("nombre", nombre)
        valoresActualizar.put("fechaCreacion", fechaCreacion)
        valoresActualizar.put("descripcion", descripcion)
        valoresActualizar.put("popularidad", popularidad)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "GeneroMusical",
                valoresActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun consultarGeneroMusicalPorId(id: String): GeneroMusical {
        val baseDatosLectura = readableDatabase
        val scriptConsultarGeneroMusical = """
            SELECT * FROM GeneroMusical WHERE id = ?
            """.trimIndent()

        val parametrosConsultaLectura = arrayOf(id)

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarGeneroMusical,
            parametrosConsultaLectura
        )

        val existeGeneroMusical = resultadoConsultaLectura.moveToFirst()
        val GeneroMusicalEncontrado =
            GeneroMusical(0, "", SimpleDateFormat("dd/MM/yyyy").parse("12/01/2023"), "", 0)
        if (existeGeneroMusical) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val fechaCreacion =
                    resultadoConsultaLectura.getString(3) // Columna indice 2 -> FECHA_CREACION
                val descripcion =
                    resultadoConsultaLectura.getString(2) // Columna indice 3 -> DESCRIPCION
                val popularidad =
                    resultadoConsultaLectura.getInt(4) // Columna indice 4 -> POPULARIDAD

                if (id != null) {
                    GeneroMusicalEncontrado.id = id
                    GeneroMusicalEncontrado.nombre = nombre
                    GeneroMusicalEncontrado.fechaCreacion =
                        SimpleDateFormat("dd/MM/yyyy").parse(fechaCreacion)
                    GeneroMusicalEncontrado.descripcion = descripcion
                    GeneroMusicalEncontrado.popularidad = popularidad
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return GeneroMusicalEncontrado
    }


}