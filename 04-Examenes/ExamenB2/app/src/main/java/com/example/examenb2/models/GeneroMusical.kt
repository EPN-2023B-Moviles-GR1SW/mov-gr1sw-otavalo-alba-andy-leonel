package com.example.examenb2.models

import java.util.Date

class GeneroMusical(
    var id: String,
    var nombre: String,
    var fechaCreacion: Date,
    var descripcion: String,
    var popularidad: Int
) {
    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }
}