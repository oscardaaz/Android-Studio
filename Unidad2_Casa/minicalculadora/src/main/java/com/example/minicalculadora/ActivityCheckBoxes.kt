package com.example.minicalculadora

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout


class ActivityCheckBoxes : AppCompatActivity() {

    private lateinit var tilCbNum1: TextInputLayout
    private lateinit var tilCbNum2: TextInputLayout
    private lateinit var cbSumar: CheckBox
    private lateinit var cbRestar: CheckBox
    private lateinit var btnCbCalcular: Button
    private lateinit var tvCbResultado: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_boxes)

        tilCbNum1 = findViewById(R.id.tilCbNum1)
        tilCbNum2 = findViewById(R.id.tilCbNum2)
        cbSumar = findViewById(R.id.cbSumar)
        cbRestar = findViewById(R.id.cbRestar)
        btnCbCalcular = findViewById(R.id.btnCbCalcular)
        tvCbResultado = findViewById(R.id.tvCbResultado)


        btnCbCalcular.setOnClickListener {
            realizarCalculo()
        }


    }
    private fun realizarCalculo() {
        val n1 = tilCbNum1.editText?.text.toString().toDouble()
        val n2 = tilCbNum2.editText?.text.toString().toDouble()

        val resultado = StringBuilder()

        if (cbSumar.isChecked) {
            resultado.append("Suma: ${n1 + n2} ")
        }
        if (cbRestar.isChecked) {
            resultado.append("Resta: ${n1 - n2} ")
        }
        tvCbResultado.text = resultado
    }
}
