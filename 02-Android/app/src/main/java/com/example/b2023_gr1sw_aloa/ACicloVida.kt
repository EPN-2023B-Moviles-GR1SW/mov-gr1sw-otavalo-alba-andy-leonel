package com.example.b2023_gr1sw_aloa
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ACicloVida: AppCompatActivity() {
    var textoGlobal = ""

    fun mostrarSnackbar (texto: String) {
        textoGlobal = textoGlobal + " " + texto
        Snackbar.make(findViewById(R.id.cl_ciclo_vida), textoGlobal, Snackbar.LENGTH_LONG) .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
    }
}


