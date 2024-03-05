package com.example.examenb2.database

import com.example.examenb2.models.Cancion
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FirestoreCancion {
    companion object {
        fun getCancionesByGeneroMusical(idGenero: String, callback: (ArrayList<Cancion>) -> Unit) {
            val canciones = ArrayList<Cancion>()
            val db = Firebase.firestore

            db.collection("Canciones")
                .whereEqualTo("idGenero", idGenero)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        println("${document.id} => ${document.data}")
                        val cancion = Cancion(
                            document.id,
                            document.data["nombre"].toString(),
                            document.data["duracion"].toString().toInt(),
                            document.data["idGenero"].toString(),
                            document.data["esColaborativa"].toString().toBoolean(),
                            (document.data["fechaLanzamiento"] as com.google.firebase.Timestamp).toDate()
                        )
                        canciones.add(cancion)
                    }
                    callback(canciones)
                }
                .addOnFailureListener { exception ->
                    println("Error getting documents: $exception")
                    callback(ArrayList())
                }
        }

        fun createCancion(cancion: Cancion, callback: (Boolean) -> Unit) {
            val db = Firebase.firestore

            val cancionDoc = hashMapOf(
                "nombre" to cancion.nombre,
                "duracion" to cancion.duracion,
                "idGenero" to cancion.idGenero,
                "esColaborativa" to cancion.esColaborativa,
                "fechaLanzamiento" to cancion.fechaLanzamiento
            )

            db.collection("Canciones")
                .add(cancionDoc)
                .addOnSuccessListener { documentReference ->
                    println("DocumentSnapshot added with ID: ${documentReference.id}")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    println("Error adding document: $e")
                    callback(false)
                }
        }

        fun getCancionById(idCancion: String, callback: (Cancion?) -> Unit) {
            val db = Firebase.firestore

            db.collection("Canciones")
                .document(idCancion)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        println("DocumentSnapshot data: ${document.data}")
                        val cancion = Cancion(
                            document.id,
                            document.data?.get("nombre").toString(),
                            document.data?.get("duracion").toString().toInt(),
                            document.data?.get("idGenero").toString(),
                            document.data?.get("esColaborativa").toString().toBoolean(),
                            (document.data?.get("fechaLanzamiento") as com.google.firebase.Timestamp).toDate()
                        )
                        callback(cancion)
                    } else {
                        println("No such document")
                        callback(null)
                    }
                }
                .addOnFailureListener { exception ->
                    println("get failed with $exception")
                    callback(null)
                }
        }

        fun updateCancion(cancion: Cancion, callback: (Boolean) -> Unit) {
            val db = Firebase.firestore

            val cancionDoc = hashMapOf(
                "nombre" to cancion.nombre,
                "duracion" to cancion.duracion,
                "idGenero" to cancion.idGenero,
                "esColaborativa" to cancion.esColaborativa,
                "fechaLanzamiento" to cancion.fechaLanzamiento
            )

            db.collection("Canciones")
                .document(cancion.id)
                .set(cancionDoc)
                .addOnSuccessListener {
                    println("DocumentSnapshot successfully updated!")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    println("Error updating document: $e")
                    callback(false)
                }
        }

        fun deleteCancion(idCancion: String, callback: (Boolean) -> Unit) {
            val db = Firebase.firestore

            db.collection("Canciones")
                .document(idCancion)
                .delete()
                .addOnSuccessListener {
                    println("DocumentSnapshot successfully deleted!")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    println("Error deleting document: $e")
                    callback(false)
                }
        }
    }

}