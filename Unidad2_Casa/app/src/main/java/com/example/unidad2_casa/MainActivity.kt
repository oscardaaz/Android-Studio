package com.example.unidad2_casa

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unidad2.R

class MainActivity : AppCompatActivity() {

    private lateinit var ejemplos: TextView
    private lateinit var linear: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ejemplos = findViewById<TextView>(R.id.tvEjemplos)
        ejemplos.text = "Texto cambiado" // Equivalente a ejemplos.setText en Java

        linear = findViewById<LinearLayout>(R.id.layout)
        val nuevoTexto = TextView(this)
        nuevoTexto.text = "TestView creado mediandte código"
        linear.addView(nuevoTexto)


    }
}