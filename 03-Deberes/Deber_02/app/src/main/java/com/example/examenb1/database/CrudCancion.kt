package com.example.examenb1.database

import com.example.examenb1.models.Cancion
import java.util.Date

class CrudCancion {
    fun crearCancion(
        id: Int,
        nombre: String,
        fechaLanzamiento: Date,
        duracion: Int,
        idGenero: Int,
        esColaborativa: Boolean
    ) {

            val cancion = Cancion(
                id,
                nombre,
                duracion,
                DBMemoria.arregloGeneroMusical.find { genero -> genero.id == idGenero }!!,
                esColaborativa,
                fechaLanzamiento
            )

            DBMemoria.arregloCancion.add(cancion)
    }

    fun editarCancion(
        id: Int,
        nombre: String,
        fechaLanzamiento: Date,
        duracion: Int,
        idGenero: Int,
        esColaborativa: Boolean
    ) {
        val cancion = Cancion(
            id,
            nombre,
            duracion,
            DBMemoria.arregloGeneroMusical.find { genero -> genero.id == idGenero }!!,
            esColaborativa,
            fechaLanzamiento
        )

        var posicion = -1

        DBMemoria.arregloCancion.forEachIndexed { index, cancion ->
            if (cancion.id == id) {
                posicion = index
            }
        }
        DBMemoria.arregloCancion[posicion] = cancion
    }
}