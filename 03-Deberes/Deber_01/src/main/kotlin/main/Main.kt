package main
import models.Cancion
import models.GeneroMusical
import repositories.CancionRepository
import repositories.GeneroMusicalRepository
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val generoRepository = GeneroMusicalRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\GenerosMusicales.txt")
    val cancionRepository = CancionRepository(System.getProperty("user.dir")+ "\\src\\main\\kotlin\\data\\Canciones.txt")

    val scanner = Scanner(System.`in`)
    var opcion: Int

    do {
        println("=== Menú CRUD ===")
        println("1. Agregar Género Musical")
        println("2. Agregar Canción")
        println("3. Mostrar Géneros Musicales")
        println("4. Mostrar Canciones")
        println("5. Actualizar Género Musical")
        println("6. Actualizar Canción")
        println("7. Eliminar Género Musical")
        println("8. Eliminar Canción")
        println("9. Salir")

        print("Seleccione una opción: ")
        opcion = scanner.nextInt()

        when (opcion) {
            1 -> {
                agregarGenero(generoRepository)
            }
            2 -> {
                agregarCancion(generoRepository, cancionRepository)
            }
            3 -> {
                mostrarGeneros(generoRepository)
            }
            4 -> {
                mostrarCanciones(cancionRepository)
            }
            5 -> {
                actualizarGenero(generoRepository)
            }
            6 -> {
                actualizarCancion(generoRepository, cancionRepository)
            }
            7 -> {
                eliminarGenero(generoRepository)
            }
            8 -> {
                eliminarCancion(cancionRepository)
            }
            9 -> {
                println("Saliendo de la aplicación.")
            }
            else -> println("Opción no válida. Inténtelo nuevamente.")
        }
    } while (opcion != 9)
}

fun agregarGenero(generoRepository: GeneroMusicalRepository) {
    println("=== Agregar Género Musical ===")
    print("ID: ")
    val id = readLine()?.toInt() ?: 0

    print("Nombre: ")
    val nombre = readLine() ?: ""

    print("Fecha de Creación (dd/MM/yyyy): ")
    val fechaCreacionStr = readLine() ?: ""
    val fechaCreacion = SimpleDateFormat("dd/MM/yyyy").parse(fechaCreacionStr)

    print("Descripción: ")
    val descripcion = readLine() ?: ""

    print("Popularidad: ")
    val popularidad = readLine()?.toInt() ?: 0

    val nuevoGenero = GeneroMusical(id, nombre, fechaCreacion, descripcion, popularidad)
    generoRepository.guardarGenero(nuevoGenero)

    println("Género Musical agregado correctamente.\n")
}

fun agregarCancion(generoRepository: GeneroMusicalRepository, cancionRepository: CancionRepository) {
    println("=== Agregar Canción ===")
    print("ID: ")
    val id = readLine()?.toInt() ?: 0

    print("Nombre: ")
    val nombre = readLine() ?: ""

    print("Duración: ")
    val duracion = readLine()?.toInt() ?: 0

    print("ID del Género Musical: ")
    val idGenero = readLine()?.toInt() ?: 0
    val genero = generoRepository.obtenerGenero(idGenero)

    print("Fecha de Lanzamiento (dd/MM/yyyy): ")
    val fechaLanzamientoStr = readLine() ?: ""
    val fechaLanzamiento = SimpleDateFormat("dd/MM/yyyy").parse(fechaLanzamientoStr)

    print("Es Colaborativa (true/false): ")
    val esColaborativa = readLine()?.toBoolean() ?: false

    val nuevaCancion = Cancion(id, nombre, duracion, genero,esColaborativa,fechaLanzamiento)
    cancionRepository.guardarCancion(nuevaCancion)

    println("Canción agregada correctamente.\n")
}

fun mostrarGeneros(generoRepository: GeneroMusicalRepository) {
    println("=== Géneros Musicales ===")
    val generos = generoRepository.obtenerGeneros()
    generos.forEach { println(it) }
    println()
}

fun mostrarCanciones(cancionRepository: CancionRepository) {
    println("=== Canciones ===")
    val canciones = cancionRepository.obtenerCanciones()
    canciones.forEach { println(it) }
    println()
}


fun actualizarGenero(generoRepository: GeneroMusicalRepository) {
    println("=== Actualizar Género Musical ===")
    // Capturar ID del género a actualizar
    print("Ingrese el ID del Género Musical a actualizar: ")
    val idActualizar = readLine()?.toIntOrNull()

    if (idActualizar != null) {
        // Verificar si el género existe
        val generoExistente = generoRepository.obtenerGenero(idActualizar)

        // Si el género existe, permitir la actualización
        if (generoExistente != null) {
            // Capturar nuevos datos del usuario...
            print("Nuevo nombre (Enter para mantener el actual '${generoExistente.nombre}'): ")
            val nuevoNombre = readLine()?.takeIf { it.isNotBlank() } ?: generoExistente.nombre

            print("Nueva fecha de Creación (dd/MM/yyyy) (Enter para mantener la actual '${SimpleDateFormat("dd/MM/yyyy").format(generoExistente.fechaCreacion)}'): ")
            val nuevaFechaCreacionStr = readLine()
            val nuevaFechaCreacion = if (nuevaFechaCreacionStr.isNullOrBlank()) generoExistente.fechaCreacion else SimpleDateFormat("dd/MM/yyyy").parse(nuevaFechaCreacionStr)

            print("Nueva descripción (Enter para mantener la actual '${generoExistente.descripcion}'): ")
            val nuevaDescripcion = readLine()?.takeIf { it.isNotBlank() } ?: generoExistente.descripcion

            print("Nueva popularidad (Enter para mantener la actual '${generoExistente.popularidad}'): ")
            val nuevaPopularidad = readLine()?.toIntOrNull() ?: generoExistente.popularidad

            val generoActualizado = GeneroMusical(idActualizar, nuevoNombre, nuevaFechaCreacion, nuevaDescripcion, nuevaPopularidad)
            generoRepository.actualizarGenero(generoActualizado)
            println("Género Musical actualizado correctamente.\n")
        } else {
            println("No se encontró un Género Musical con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}


fun actualizarCancion(generoRepository: GeneroMusicalRepository, cancionRepository: CancionRepository) {
    println("=== Actualizar Canción ===")
    // Capturar ID de la canción a actualizar
    print("Ingrese el ID de la Canción a actualizar: ")
    val idActualizar = readLine()?.toIntOrNull()

    if (idActualizar != null) {
        // Verificar si la canción existe
        val cancionExistente = cancionRepository.obtenerCancion(idActualizar)

        // Si la canción existe, permitir la actualización
        if (cancionExistente != null) {
            // Capturar nuevos datos del usuario...
            print("Nuevo nombre (Enter para mantener el actual '${cancionExistente.nombre}'): ")
            val nuevoNombre = readLine()?.takeIf { it.isNotBlank() } ?: cancionExistente.nombre

            print("Nueva duración (Enter para mantener la actual '${cancionExistente.duracion}'): ")
            val nuevaDuracion = readLine()?.toIntOrNull() ?: cancionExistente.duracion

            print("Nuevo ID del Género Musical (Enter para mantener el actual '${cancionExistente.genero.id}'): ")
            val nuevoIdGenero = readLine()?.toIntOrNull() ?: cancionExistente.genero.id
            val nuevoGenero = generoRepository.obtenerGenero(nuevoIdGenero)

            print("Nueva fecha de Lanzamiento (dd/MM/yyyy) (Enter para mantener la actual '${SimpleDateFormat("dd/MM/yyyy").format(cancionExistente.fechaLanzamiento)}'): ")
            val nuevaFechaLanzamientoStr = readLine()
            val nuevaFechaLanzamiento = if (nuevaFechaLanzamientoStr.isNullOrBlank()) cancionExistente.fechaLanzamiento else SimpleDateFormat("dd/MM/yyyy").parse(nuevaFechaLanzamientoStr)

            print("Es Colaborativa (true/false) (Enter para mantener el actual '${cancionExistente.esColaborativa}'): ")
            val nuevaEsColaborativa = readLine()?.toBoolean() ?: cancionExistente.esColaborativa

            val cancionActualizada = Cancion(idActualizar, nuevoNombre, nuevaDuracion, nuevoGenero, nuevaEsColaborativa,nuevaFechaLanzamiento)
            cancionRepository.actualizarCancion(cancionActualizada)
            println("Canción actualizada correctamente.\n")
        } else {
            println("No se encontró una Canción con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}



fun eliminarGenero(generoRepository: GeneroMusicalRepository) {
    println("=== Eliminar Género Musical ===")
    // Capturar ID del género a eliminar
    print("Ingrese el ID del Género Musical a eliminar: ")
    val idEliminar = readLine()?.toIntOrNull()

    if (idEliminar != null) {
        // Verificar si el género existe
        val generoExistente = generoRepository.obtenerGenero(idEliminar)

        // Si el género existe, permitir la eliminación
        if (generoExistente != null) {
            generoRepository.eliminarGenero(idEliminar)
            println("Género Musical eliminado correctamente.\n")
        } else {
            println("No se encontró un Género Musical con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}

fun eliminarCancion(cancionRepository: CancionRepository) {
    println("=== Eliminar Canción ===")
    // Capturar ID de la canción a eliminar
    print("Ingrese el ID de la Canción a eliminar: ")
    val idEliminar = readLine()?.toIntOrNull()

    if (idEliminar != null) {
        // Verificar si la canción existe
        val cancionExistente = cancionRepository.obtenerCancion(idEliminar)

        // Si la canción existe, permitir la eliminación
        if (cancionExistente != null) {
            cancionRepository.eliminarCancion(idEliminar)
            println("Canción eliminada correctamente.\n")
        } else {
            println("No se encontró una Canción con el ID proporcionado.\n")
        }
    } else {
        println("Entrada no válida. El ID debe ser un número entero.\n")
    }
}
