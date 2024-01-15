package com.example.examenb1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}




