package com.example.minicalculadora

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class ActivitySpinner : AppCompatActivity() {

    private lateinit var tilSpNum1: TextInputLayout
    private lateinit var tilSpNum2: TextInputLayout
    private lateinit var spinner: Spinner
    private lateinit var btnSpCalcular: Button
    private lateinit var tvSpResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_spinner)
        tilSpNum1 = findViewById(R.id.tilSpNum1)
        tilSpNum2 = findViewById(R.id.tilSpNum2)
        spinner = findViewById(R.id.spinner)
        btnSpCalcular = findViewById(R.id.btnSpCalcular)
        tvSpResultado = findViewById(R.id.tvSpResultado)

        btnSpCalcular.setOnClickListener {
            realizarCalculo()
        }
    }

    private fun realizarCalculo() {
        val n1 = tilSpNum1.editText?.text.toString().toDouble()
        val n2 = tilSpNum2.editText?.text.toString().toDouble()



        //Opcion 1
//        val operacion = spinner.selectedItem.toString()
//        val resultado = StringBuilder()
//        if (operacion == "Sumar") {
//            resultado.append("Suma: ${n1 + n2} ")
//        } else if (operacion == "Restar") {
//            resultado.append("Resta: ${n1 - n2} ")
//        }

        // Opcion 2
//        val operacion = spinner.selectedItem.toString()
//        val resultado = if (operacion == "Sumar") {
//            "Suma: ${n1 + n2} "
//        } else if (operacion == "Restar") {
//            "Resta: ${n1 - n2} "
//        }else {
//            "Seleccione una operación"
//        }

        // Opcion 3
        val posicionSeleccionada = spinner.selectedItemPosition
        val resultado = when (posicionSeleccionada) {
           // 0 -> "Seleccione una opcion valida"
            1 -> "Suma: ${n1 + n2} "
            2 -> "Resta: ${n1 - n2} "
            else -> "Opcion no valida"
        }

        tvSpResultado.text = resultado

    }

}
