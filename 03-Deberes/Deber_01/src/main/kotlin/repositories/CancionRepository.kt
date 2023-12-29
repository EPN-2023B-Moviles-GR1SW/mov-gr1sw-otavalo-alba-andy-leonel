package repositories
import models.Cancion
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CancionRepository(private val fileName: String) {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun guardarCancion(cancion: Cancion) {
        val registro = "${cancion.id},${cancion.nombre},${cancion.duracion}," +
                "${cancion.genero.id},${dateFormat.format(cancion.fechaLanzamiento)}," +
                "${cancion.esColaborativa}\n"
        File(fileName).appendText(registro)
    }

    fun obtenerCancion(id: Int): Cancion {
        val line = File(fileName).readLines().find { it.startsWith("$id,") }
        val campos = line?.split(",") ?: throw NoSuchElementException("No se encontró una canción con el ID $id")

        return Cancion(
            id = campos[0].toInt(),
            nombre = campos[1],
            duracion = campos[2].toInt(),
            genero = GeneroMusicalRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\GenerosMusicales.txt").obtenerGenero(campos[3].toInt()),
            fechaLanzamiento = dateFormat.parse(campos[4]),
            esColaborativa = campos[5].toBoolean()
        )
    }

    fun obtenerCanciones(): List<Cancion> {
        return File(fileName).readLines().map { line ->
            val campos = line.split(",")
            Cancion(
                id = campos[0].toInt(),
                nombre = campos[1],
                duracion = campos[2].toInt(),
                genero = GeneroMusicalRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\GenerosMusicales.txt").obtenerGenero(campos[3].toInt()),
                fechaLanzamiento = dateFormat.parse(campos[4]),
                esColaborativa = campos[5].toBoolean()
            )
        }
    }

    fun actualizarCancion(cancion: Cancion) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.map { line ->
            if (line.startsWith("${cancion.id},")) {
                "${cancion.id},${cancion.nombre},${cancion.duracion}," +
                        "${cancion.genero.id},${dateFormat.format(cancion.fechaLanzamiento)}," +
                        "${cancion.esColaborativa}"
            } else {
                line
            }
        }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }

    fun eliminarCancion(id: Int) {
        val lineas = File(fileName).readLines()
        val nuevaLista = lineas.filter { !it.startsWith("$id,") }
        File(fileName).writeText(nuevaLista.joinToString("\n"))
    }

    fun obtenerCancionesPorGenero(idGenero: Int): List<Cancion> {
        val lineas = File(fileName).readLines()
        val cancionesDelGenero = lineas
            .filter { it.split(",")[3].toInt() == idGenero }
            .map { crearCancionDesdeString(it) }
        return cancionesDelGenero
    }

    private fun crearCancionDesdeString(linea: String): Cancion {
        val generoRepository = GeneroMusicalRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\GenerosMusicales.txt")
        val campos = linea.split(",")
        val id = campos[0].toInt()
        val nombre = campos[1]
        val duracion = campos[2].toInt()
        val idGenero = campos[3].toInt()
        val fechaLanzamiento = SimpleDateFormat("dd/MM/yyyy").parse(campos[4])
        val genero = generoRepository.obtenerGenero(idGenero)
        val esColaborativa = campos[5].toBoolean()

        return Cancion(id, nombre, duracion, genero,esColaborativa,fechaLanzamiento)
    }
}
