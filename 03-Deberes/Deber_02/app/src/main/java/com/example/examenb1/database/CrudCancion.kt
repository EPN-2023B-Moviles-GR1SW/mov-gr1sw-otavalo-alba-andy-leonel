package com.example.examenb1.database

class CrudCancion {
    fun crearCancion(
        nombre: String,
        duracion: Int,
        generoId: Int,
        esColaborativa: Boolean,
        fechaLanzamiento: String,
    ) :Boolean{

        val respuesta = DBSQLite.tablaCancion!!.crearCancion(
            nombre, duracion,generoId,esColaborativa,fechaLanzamiento
        )

        return respuesta
    }

    fun editarCancion(
        id: Int,
        nombre: String,
        duracion: Int,
        generoId: Int,
        esColaborativa: Boolean,
        fechaLanzamiento: String,
    ) {
        val respuesta = DBSQLite.tablaCancion!!.actualizarCancionFormulario(
            nombre, duracion,generoId,esColaborativa,fechaLanzamiento,id
        )
    }

    fun eliminarCancion(id: Int):Boolean {
        val respuesta = DBSQLite.tablaCancion!!.eliminarCancionFormulario(id)
        return respuesta
    }
}