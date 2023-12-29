package models

import repositories.GeneroMusicalRepository
import java.text.SimpleDateFormat
import java.util.*

class GeneroMusical(
    var id: Int,
    var nombre: String,
    var fechaCreacion: Date,
    var descripcion: String,
    var popularidad: Int
) {
    private val generoRepository = GeneroMusicalRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\GenerosMusicales.txt")

    constructor(id: Int, nombre: String, fechaCreacion: String, descripcion: String, popularidad: Int) :
            this(id, nombre, SimpleDateFormat("dd/MM/yyyy").parse(fechaCreacion), descripcion, popularidad)

    fun guardarGenero() {
        generoRepository.guardarGenero(this)
        println("Guardando género: $nombre")
    }

    fun obtenerGenero(id: Int): GeneroMusical {
        return generoRepository.obtenerGenero(id)
    }

    fun obtenerGeneros(): List<GeneroMusical> {
        return generoRepository.obtenerGeneros()
    }

    fun actualizarGenero() {
        generoRepository.actualizarGenero(this)
        println("Actualizando género: $nombre")
    }

    fun eliminarGenero() {
        generoRepository.eliminarGenero(this.id)
        println("Eliminando género: $nombre")
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "GeneroMusical(id=$id, nombre='$nombre', fechaCreacion=${dateFormat.format(fechaCreacion)}, descripcion='$descripcion', popularidad=$popularidad)"
    }
}
