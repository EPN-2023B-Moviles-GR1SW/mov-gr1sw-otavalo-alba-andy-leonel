package com.example.examenb1.database

import com.example.examenb1.models.Cancion
import com.example.examenb1.models.GeneroMusical
import java.text.SimpleDateFormat

class DBMemoria {

    companion object {
        val arregloGeneroMusical = arrayListOf<GeneroMusical>()
        val arregloCancion = arrayListOf<Cancion>()

        init {

            //Registros para el modelo GeneroMusical

            arregloGeneroMusical.add(
                GeneroMusical(
                    1,
                    "Rock",
                    SimpleDateFormat("dd/MM/yyyy").parse("12/06/1980"),
                    "Es música con letras profundas y con un ritmo fuerte",
                    9
                )
            )

            arregloGeneroMusical.add(
                GeneroMusical(
                    2,
                    "Pop",
                    SimpleDateFormat("dd/MM/yyyy").parse("12/06/1980"),
                    "Es música cálida y con un ritmo suave y divertido",
                    9
                )
            )

            arregloGeneroMusical.add(
                GeneroMusical(
                    3,
                    "Reggaeton",
                    SimpleDateFormat("dd/MM/yyyy").parse("12/06/1980"),
                    "Es música con un ritmo fuerte y con letras divertidas",
                    9
                )
            )

            //Registros para el modelo Cancion

            arregloCancion.add(
                Cancion(
                    1,
                    "Bohemian Rhapsody",
                    3,
                    arregloGeneroMusical.find { generoMusical ->
                        generoMusical.id == 1
                    }!!,
                    false,
                    SimpleDateFormat("dd/MM/yyyy").parse("12/06/1980"),

                    )
            )

            arregloCancion.add(
                Cancion(
                    2,
                    "We Will Rock You",
                    3,
                    arregloGeneroMusical.find { generoMusical ->
                        generoMusical.id == 1
                    }!!,
                    false,
                    SimpleDateFormat("dd/MM/yyyy").parse("12/06/1980"),

                    )
            )

            arregloCancion.add(
                Cancion(
                    3,
                    "Another One Bites the Dust",
                    3,
                    arregloGeneroMusical.find { generoMusical ->
                        generoMusical.id == 2
                    }!!,
                    false,
                    SimpleDateFormat("dd/MM/yyyy").parse("12/06/1980"),

                    )
            )

            arregloCancion.add(
                Cancion(
                    4,
                    "My space",
                    3,
                    arregloGeneroMusical.find { generoMusical ->
                        generoMusical.id == 3
                    }!!,
                    true,
                    SimpleDateFormat("dd/MM/yyyy").parse("12/06/2010"),

                    )
            )
        }
    }
}


