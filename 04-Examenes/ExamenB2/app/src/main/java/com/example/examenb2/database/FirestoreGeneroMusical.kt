package com.example.examenb2.database

import com.example.examenb2.models.GeneroMusical
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FirestoreGeneroMusical {

    companion object {
        fun getAllGenerosMusicales(callback: (ArrayList<GeneroMusical>) -> Unit) {
            val generosMusicales = ArrayList<GeneroMusical>()
            val db = Firebase.firestore

            db.collection("GenerosMusicales")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        println("${document.id} => ${document.data}")
                        val genero = GeneroMusical(
                            document.id,
                            document.data["nombre"].toString(),
                            (document.data["fechaCreacion"] as com.google.firebase.Timestamp).toDate(),
                            document.data["descripcion"].toString(),
                            document.data["popularidad"].toString().toInt()
                        )
                        generosMusicales.add(genero)
                    }
                    callback(generosMusicales)
                }
                .addOnFailureListener { exception ->
                    println("Error getting documents: $exception")
                    callback(ArrayList()) // Manejo de errores, devolver una lista vacía
                }
        }

        fun createGeneroMusical(genero: GeneroMusical, callback: (Boolean) -> Unit) {
            val db = Firebase.firestore
            val generoMusical = hashMapOf(
                "nombre" to genero.nombre,
                "fechaCreacion" to genero.fechaCreacion,
                "descripcion" to genero.descripcion,
                "popularidad" to genero.popularidad
            )

            db.collection("GenerosMusicales")
                .add(generoMusical)
                .addOnSuccessListener { documentReference ->
                    println("DocumentSnapshot added with ID: ${documentReference.id}")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    println("Error adding document: $e")
                    callback(false)
                }
        }

        fun updateGeneroMusical(genero: GeneroMusical, callback: (Boolean) -> Unit) {
            val db = Firebase.firestore
            val generoMusical = hashMapOf(
                "nombre" to genero.nombre,
                "fechaCreacion" to genero.fechaCreacion,
                "descripcion" to genero.descripcion,
                "popularidad" to genero.popularidad
            )

            db.collection("GenerosMusicales")
                .document(genero.id)
                .set(generoMusical)
                .addOnSuccessListener {
                    println("DocumentSnapshot successfully updated!")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    println("Error updating document: $e")
                    callback(false)
                }
        }

        fun getGeneroMusicalById(idGenero: String, callback: (GeneroMusical?) -> Unit) {
            val db = Firebase.firestore
            db.collection("GenerosMusicales")
                .document(idGenero)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        println("DocumentSnapshot data: ${document.data}")
                        val genero = GeneroMusical(
                            document.id,
                            document.data?.get("nombre").toString(),
                            (document.data?.get("fechaCreacion") as com.google.firebase.Timestamp).toDate(),
                            document.data?.get("descripcion").toString(),
                            document.data?.get("popularidad").toString().toInt()
                        )
                        callback(genero)
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

        fun deleteGeneroMusical(idGenero: String, callback: (Boolean) -> Unit) {
            val db = Firebase.firestore
            db.collection("GenerosMusicales")
                .document(idGenero)
                .delete()
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                }
        }


        fun deleteCancionesByGeneroMusical(idGenero: String, callback: (Boolean) -> Unit) {
            val db = Firebase.firestore
            db.collection("Canciones")
                .whereEqualTo("idGenero", idGenero)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        db.collection("Canciones")
                            .document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                callback(true)
                            }
                            .addOnFailureListener {
                                callback(false)
                            }
                    }
                }
                .addOnFailureListener {
                    callback(false)
                }
        }
    }
}
