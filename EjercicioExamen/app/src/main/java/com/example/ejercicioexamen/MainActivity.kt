package com.example.ejercicioexamen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnCb1 : CheckBox
    private lateinit var btnCb2 : CheckBox
    private lateinit var btnRB : Button
    private lateinit var rb1 : RadioButton
    private lateinit var rb2 : RadioButton
    private lateinit var rb3 : RadioButton
    private lateinit var spinner : Spinner
    private lateinit var tvMain : TextView
    private lateinit var btnAbrir : Button
    private lateinit var rGroup : RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAbrir = findViewById(R.id.btnAbrir)
        rGroup = findViewById(R.id.radioGroup)
        btnCb1 = findViewById(R.id.checkBox1)
        btnCb2 = findViewById(R.id.checkBox2)
        btnRB = findViewById(R.id.btnRB)
        rb1 = findViewById(R.id.rb1)
        rb2 = findViewById(R.id.rb2)
        rb3 = findViewById(R.id.rb3)
        spinner = findViewById(R.id.spinner)
        tvMain = findViewById(R.id.tvMain)
        btnRB.setOnClickListener {
            Toast.makeText(this,"Pulsado RB", Toast.LENGTH_SHORT).show()
            Log.d("Mi App", "Pulsado RB")

            val resultado = if (rb1.isChecked) {
                "1"
            }else if (rb2.isChecked){
                "2"
            }else {
                "3"
            }
            tvMain.text = resultado
        }


        configurarBotonAbrir()
    }

    private fun configurarBotonAbrir() {
        btnAbrir.setOnClickListener {
            Toast.makeText(this, "Pulsado Abrir", Toast.LENGTH_SHORT).show()
            Log.d("Mi App", "Pulsado Abrir")

            val intent = Intent(this, SegundaActivity::class.java)
            intent.putExtra("CB1", btnCb1.isChecked)
            intent.putExtra("CB2", btnCb2.isChecked)
            intent.putExtra("RB", rGroup.checkedRadioButtonId)
            intent.putExtra("SPINNER", spinner.selectedItemPosition)
            startActivity(intent)
        }
    }
}