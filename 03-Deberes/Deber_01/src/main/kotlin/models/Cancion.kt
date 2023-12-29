package models

import repositories.CancionRepository
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
    private val cancionRepository = CancionRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\Canciones.txt")

    fun guardarCancion() {
        cancionRepository.guardarCancion(this)
        println("Guardando canción: $nombre")
    }

    fun obtenerCancion(id: Int): Cancion {
        return cancionRepository.obtenerCancion(id)
    }

    fun obtenerCanciones(): List<Cancion> {
        return cancionRepository.obtenerCanciones()
    }

    fun actualizarCancion() {
        cancionRepository.actualizarCancion(this)
        println("Actualizando canción: $nombre")
    }

    fun eliminarCancion() {
        cancionRepository.eliminarCancion(this.id)
        println("Eliminando canción: $nombre")
    }

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return "Cancion(id=$id, nombre='$nombre', duracion=$duracion, genero=$genero, fechaLanzamiento=${dateFormat.format(fechaLanzamiento)}, esColaborativa=$esColaborativa)"
    }
}
