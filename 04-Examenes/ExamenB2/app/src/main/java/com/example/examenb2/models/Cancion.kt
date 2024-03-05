package com.example.examenb2.models

import java.util.Date

class Cancion(
    var id: String,
    var nombre: String,
    var duracion: Int,
    var idGenero: String,
    var esColaborativa: Boolean,
    var fechaLanzamiento: Date
) {
    override fun toString(): String {

        return "${nombre} "
    }
}
