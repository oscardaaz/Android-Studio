package com.example.ejercicioexamen

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SegundaActivity : AppCompatActivity() {

    private lateinit var tvUno: TextView
    private lateinit var tvDos: TextView
    private lateinit var tvRB: TextView
    private lateinit var tvSP: TextView
    private lateinit var btnCambiar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda)

        tvUno = findViewById(R.id.tvUno)
        tvDos = findViewById(R.id.tvDos)
        tvRB = findViewById(R.id.tvRB)
        tvSP = findViewById(R.id.tvSpinner)
        btnCambiar = findViewById(R.id.btnCambiar)

        var cb1 = intent.getBooleanExtra("CB1", false)
        var cb2 = intent.getBooleanExtra("CB2", false)

        tvUno.text = if (cb1 == true) "Uno: Checked"
        else "Uno: Unchecked"

        tvDos.text = if (cb2 == true) "Dos: Checked"
        else "Dos: Unchecked"

        var rbId = intent.getIntExtra("RB",-1)
        tvRB.text = when (rbId){
            R.id.rb1 -> "RB checkeado 1"
            R.id.rb2 -> "RB checkeado 2"
            R.id.rb3 -> "RB checkeado 3"
            else -> ""
        }

        btnCambiar.setOnClickListener {
            val aux = tvUno.text
            tvUno.text = tvDos.text
            tvDos.text = aux
        }
    }
}