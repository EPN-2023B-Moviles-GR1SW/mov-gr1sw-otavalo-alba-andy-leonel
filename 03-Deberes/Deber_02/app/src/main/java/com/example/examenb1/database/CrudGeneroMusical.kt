package com.example.examenb1.database

import com.example.examenb1.models.GeneroMusical
import java.util.Date

class CrudGeneroMusical {
    fun crearGeneroMusical(
        id: Int,
        nombre: String,
        fechaCreacion: Date,
        descripcion: String,
        popularidad: Int
    ) {

        val generoMusical = GeneroMusical(
            id,
            nombre,
            fechaCreacion,
            descripcion,
            popularidad
        )

        DBMemoria.arregloGeneroMusical.add(generoMusical)
    }

    fun editarGeneroMusical(
        id: Int,
        nombre: String,
        fechaCreacion: Date,
        descripcion: String,
        popularidad: Int
    ) {
        val generoMusical = GeneroMusical(
            id,
            nombre,
            fechaCreacion,
            descripcion,
            popularidad
        )

        val generoAux = DBMemoria.arregloGeneroMusical.find { generoMusical ->
            generoMusical.id == id
        }

        val posicion = DBMemoria.arregloGeneroMusical.indexOf(generoAux)
        DBMemoria.arregloGeneroMusical[posicion] = generoMusical
    }

    fun eliminarCancionesDelGenero(id: Int) {
        val canciones = DBMemoria.arregloCancion.filter { cancion ->
            cancion.genero.id == id
        }

        canciones.forEach { cancion ->
            DBMemoria.arregloCancion.removeIf { cancionAux ->
                cancionAux.id == cancion.id
            }
        }
    }

}