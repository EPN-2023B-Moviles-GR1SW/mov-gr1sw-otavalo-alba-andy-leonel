package repositories

import models.GeneroMusical
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class GeneroMusicalRepository(private val fileName: String) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    fun guardarGenero(genero: GeneroMusical) {
        val registro = "${genero.id},${genero.nombre},${dateFormat.format(genero.fechaCreacion)},${genero.descripcion},${genero.popularidad}\n"
        File(fileName).appendText(registro)
    }

    fun obtenerGenero(id: Int): GeneroMusical {
        val line = File(fileName).readLines().find { it.startsWith("$id,") }
        val campos = line?.split(",") ?: throw NoSuchElementException("No se encontró un género con el ID $id")

        return GeneroMusical(
            id = campos[0].toInt(),
            nombre = campos[1],
            fechaCreacion = dateFormat.parse(campos[2]),
            descripcion = campos[3],
            popularidad = campos[4].toInt()
        )
    }

    fun obtenerGeneros(): List<GeneroMusical> {
        return File(fileName).readLines().map { line ->
            val campos = line.split(",")
            GeneroMusical(
                id = campos[0].toInt(),
                nombre = campos[1],
                fechaCreacion = dateFormat.parse(campos[2]),
                descripcion = campos[3],
                popularidad = campos[4].toInt()
            )
        }
    }

    fun actualizarGenero(genero: GeneroMusical) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.map { line ->
            if (line.startsWith("${genero.id},")) {
                "${genero.id},${genero.nombre},${dateFormat.format(genero.fechaCreacion)},${genero.descripcion},${genero.popularidad}"
            } else {
                line
            }
        }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }

    fun eliminarGenero(id: Int) {
        val cancionRepository = CancionRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\Canciones.txt")
        // Obtener todas las canciones asociadas al género musical
        val cancionesAsociadas = cancionRepository.obtenerCancionesPorGenero(id)

        // Eliminar el género musical
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.filter { !it.startsWith("$id,") }
        File(fileName).writeText(nuevaLista.joinToString("\n"))

        // Eliminar en cascada las canciones asociadas
        cancionesAsociadas.forEach { cancionRepository.eliminarCancion(it.id) }
    }




}
