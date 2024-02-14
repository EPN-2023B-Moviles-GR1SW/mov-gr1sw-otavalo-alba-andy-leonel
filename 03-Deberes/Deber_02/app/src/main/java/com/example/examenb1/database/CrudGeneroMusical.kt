package com.example.examenb1.database

class CrudGeneroMusical {
    fun crearGeneroMusical(
        nombre: String,
        fechaCreacion: String,
        descripcion: String,
        popularidad: Int
    ): Boolean {

        val respuesta = DBSQLite.tablaGeneroMusical!!.crearGeneroMusical(
            nombre, descripcion, fechaCreacion, popularidad
        )
        return respuesta
    }

    fun editarGeneroMusical(
        id: Int,
        nombre: String,
        fechaCreacion: String,
        descripcion: String,
        popularidad: Int
    ):Boolean {

        val respuesta = DBSQLite.tablaGeneroMusical!!.actualizarGeneroMusicalFormulario(
            nombre, descripcion, fechaCreacion, popularidad,id
        )

        return respuesta
    }

    fun eliminarGeneroMusical(id: Int):Boolean {
        val respuesta = DBSQLite.tablaGeneroMusical!!.eliminarGeneroMusicalFormulario(id)
        return respuesta
    }

}