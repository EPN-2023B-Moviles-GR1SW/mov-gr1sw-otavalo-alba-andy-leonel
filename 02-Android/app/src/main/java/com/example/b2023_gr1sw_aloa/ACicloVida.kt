package com.example.b2023_gr1sw_aloa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal= ""
    fun mostrarSnackbar(texto:String){
        textoGlobal = textoGlobal + "" + texto
        Snackbar
            .make(
                findViewById(R.id.cl_ciclo_vida),
                textoGlobal,
                Snackbar.LENGTH_INDEFINITE
            )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("OnCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("OnStart")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("OnRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackbar("OnPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackbar("OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("OnDestroy")
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("textoGuardado",textoGlobal)
        }
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        val textoRecuperado:String? = savedInstanceState.getString("textoGuardado")
        if(textoRecuperado!=null){
            mostrarSnackbar(textoRecuperado)
            textoGlobal=textoRecuperado
        }
    }
}
