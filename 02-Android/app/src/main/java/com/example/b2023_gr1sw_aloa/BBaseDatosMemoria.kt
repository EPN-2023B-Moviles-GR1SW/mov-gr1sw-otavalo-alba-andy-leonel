package com.example.b2023_gr1sw_aloa

class BBaseDatosMemoria {
    // EMPEZAR EL COMPANION OBJECT
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "Leonel", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Andy", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "Leoandy", "c@c.com")
                )
        }
    }
}