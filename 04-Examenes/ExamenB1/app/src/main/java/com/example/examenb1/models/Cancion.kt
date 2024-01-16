package com.example.examenb1.models

import java.text.SimpleDateFormat
import java.util.Date

class Cancion(
    var id: Int,
    var nombre: String,
    var duracion: Int,
    var genero: GeneroMusical,
    var esColaborativa: Boolean,
    var fechaLanzamiento: Date
) {
    override fun toString(): String {

        return "${nombre}"
    }
}
