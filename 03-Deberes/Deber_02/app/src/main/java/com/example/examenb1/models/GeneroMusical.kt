package com.example.examenb1.models

import java.util.*

class GeneroMusical(
    var id: Int,
    var nombre: String,
    var fechaCreacion: Date,
    var descripcion: String,
    var popularidad: Int
) {
    override fun toString(): String {
        return "${nombre}"
    }
}
