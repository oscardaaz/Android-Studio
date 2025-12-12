package com.example.minicalculadora

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
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

        enableEdgeToEdge()

        setContentView(R.layout.activity_check_boxes)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cbMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tilCbNum1 = findViewById(R.id.tilCbNum1)
        tilCbNum2 = findViewById(R.id.tilCbNum2)
        cbSumar = findViewById(R.id.cbSumar)
        cbRestar = findViewById(R.id.cbRestar)
        btnCbCalcular = findViewById(R.id.btnCbCalcular)
        tvCbResultado = findViewById(R.id.tvCbResultado)

        val toolbar = findViewById<MaterialToolbar>(R.id.cbMaterialToolbar)


        toolbar.setNavigationOnClickListener {
            //finish()
            onBackPressedDispatcher.onBackPressed()
        }
        toolbar.title = "Mini-Calculadora"
        toolbar.subtitle = "CheckBoxes"

        btnCbCalcular.setOnClickListener {
            realizarCalculo()
        }

        toolbar.inflateMenu(R.menu.menu_appbar)



        toolbar.setOnMenuItemClickListener { item ->

            val n1 = tilCbNum1.editText?.text.toString().toDouble()
            val n2 = tilCbNum2.editText?.text.toString().toDouble()

            if (n1 == null || n2 == null) {
                tvCbResultado.text = "No puede haber campos vacios"
                return@setOnMenuItemClickListener false
            }

            when (item.itemId){
                R.id.action_sumar -> {
                    tvCbResultado.text = "Suma desde menu: ${n1 + n2}"
                    true
                }
                R.id.action_restar -> {
                    tvCbResultado.text = "Resta desde menu: ${n1 - n2}"
                    true
                }
                else -> false
            }

        }





    } // Fin onCreate
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
