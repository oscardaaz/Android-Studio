package com.example.minicalculadora

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class ActivityRadioButtons : AppCompatActivity() {

    private lateinit var tilRbNum1: TextInputLayout
    private lateinit var tilRbNum2: TextInputLayout
    private lateinit var rbSumar: RadioButton
    private lateinit var rbRestar: RadioButton
    private lateinit var btnRbCalcular: Button
    private lateinit var tvCbResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_buttons)

        tilRbNum1 = findViewById(R.id.tilRbNum1)
        tilRbNum2 = findViewById(R.id.tilRbNum2)
        rbSumar = findViewById(R.id.rbSumar)
        rbRestar = findViewById(R.id.rbRestar)
        btnRbCalcular = findViewById(R.id.btnRbCalcular)
        tvCbResultado = findViewById(R.id.tvRbResultado)

        btnRbCalcular.setOnClickListener {
            realizarCalculo()
        }

    }

    private fun realizarCalculo() {
        val n1 = tilRbNum1.editText?.text.toString().toDouble()
        val n2 = tilRbNum2.editText?.text.toString().toDouble()

        // Opcion 1
//       val resultado = StringBuilder()s
//        if (rbSumar.isChecked) {
//            resultado.append("Suma: ${n1 + n2} ")
//        }
//        if (rbRestar.isChecked) {
//            resultado.append("Resta: ${n1 - n2} ")
//        }

        // Opcion 2
        val resultado = if (rbSumar.isChecked){
            "Suma: ${n1 + n2} "
        } else if (rbRestar.isChecked){
            "Resta: ${n1 - n2} "
        }else {
            "Seleccione una operación"
        }

        tvCbResultado.text = resultado
    }
}