package com.example.b2023_gr1sw_aloa

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador= arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Adrian","a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Vicente","b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Carolina","b@b.com")
                )
        }
    }
}