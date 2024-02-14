package com.example.examenb1.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examenb1.models.Cancion
import java.text.SimpleDateFormat

class ESqliteHelperCancion(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto, "Deber02",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCancion =
            """
                CREATE TABLE Cancion (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(255) NOT NULL,
                duracion INTEGER NOT NULL,
                genero_id INTEGER,
                esColaborativa BOOLEAN NOT NULL,
                fechaLanzamiento DATE NOT NULL,
                FOREIGN KEY (genero_id) REFERENCES Cancion(id)
                ON UPDATE CASCADE 
                ON DELETE CASCADE
                );
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCancion)
    }

    override fun onUpgrade(bd: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    fun obtenerTodasCanciones(generoId: Int): ArrayList<Cancion> {

        var listCancion = ArrayList<Cancion>()

        val baseDatosLectura = readableDatabase
        val scriptConsultarCancion = """
            SELECT * FROM Cancion
            WHERE genero_id = ?
            """.trimIndent()

        val pametrosConsultaLectura = arrayOf(generoId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarCancion,
            pametrosConsultaLectura
        )

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val CancionEncontrado =
                    Cancion(0, "", 0, 0, false, SimpleDateFormat("dd/MM/yyyy").parse("12/01/2023"))

                val id = resultadoConsultaLectura.getInt(0) // Columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val duracion =
                    resultadoConsultaLectura.getString(2) // Columna indice 2 -> FECHA_CREACION
                val generoId = resultadoConsultaLectura.getInt(3) // Columna indice 3 -> DESCRIPCION
                val esColaborativa =
                    resultadoConsultaLectura.getInt(4) // Columna indice 4 -> POPULARIDAD
                val fechaLanzamiento =
                    resultadoConsultaLectura.getString(5) // Columna indice 4 -> POPULARIDAD

                if (
                    id != null
                ) {
                    CancionEncontrado.id = id
                    CancionEncontrado.nombre = nombre
                    CancionEncontrado.duracion = duracion.toInt()
                    CancionEncontrado.generoId = generoId
                    CancionEncontrado.esColaborativa = esColaborativa == 1
                    CancionEncontrado.fechaLanzamiento =
                        SimpleDateFormat("dd/MM/yyyy").parse(fechaLanzamiento)

                }
                listCancion.add(CancionEncontrado)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listCancion
    }

    fun crearCancion(
        nombre: String,
        duracion: Int,
        generoId: Int,
        esColaborativa: Boolean,
        fechaLanzamiento: String
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("duracion", duracion)
        valoresAGuardar.put("genero_id", generoId)
        valoresAGuardar.put("esColaborativa", esColaborativa)
        valoresAGuardar.put("fechaLanzamiento", fechaLanzamiento)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "Cancion",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarCancionFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "Cancion",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun actualizarCancionFormulario(
        nombre: String,
        duracion: Int,
        generoId: Int,
        esColaborativa: Boolean,
        fechaLanzamiento: String,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("nombre", nombre)
        valoresActualizar.put("duracion", duracion)
        valoresActualizar.put("genero_id", generoId)
        valoresActualizar.put("esColaborativa", esColaborativa)
        valoresActualizar.put("fechaLanzamiento", fechaLanzamiento)

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "Cancion",
                valoresActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun consultarCancionPorId(id: Int): Cancion {
        val baseDatosLectura = readableDatabase
        val scriptConsultarCancion = """
            SELECT * FROM Cancion WHERE id = ?
            """.trimIndent()

        val parametrosConsultaLectura = arrayOf(id.toString())

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarCancion,
            parametrosConsultaLectura
        )

        val existeCancion = resultadoConsultaLectura.moveToFirst()
        val CancionEncontrado =
            Cancion(0, "", 0, 0, false, SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"))

        if (existeCancion) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val duracion =
                    resultadoConsultaLectura.getString(2) // Columna indice 2 -> FECHA_CREACION
                val generoId = resultadoConsultaLectura.getInt(3) // Columna indice 3 -> DESCRIPCION
                val esColaborativa =
                    resultadoConsultaLectura.getInt(4) // Columna indice 4 -> POPULARIDAD
                val fechaLanzamiento =
                    resultadoConsultaLectura.getString(5) // Columna indice 4 -> POPULARIDAD

                if (
                    id != null
                ) {
                    CancionEncontrado.id = id
                    CancionEncontrado.nombre = nombre
                    CancionEncontrado.duracion = duracion.toInt()
                    CancionEncontrado.generoId = generoId
                    CancionEncontrado.esColaborativa = esColaborativa == 1
                    CancionEncontrado.fechaLanzamiento =
                        SimpleDateFormat("dd/MM/yyyy").parse(fechaLanzamiento)

                }


            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return CancionEncontrado
    }


}