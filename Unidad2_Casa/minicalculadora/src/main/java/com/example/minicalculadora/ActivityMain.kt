package com.example.minicalculadora

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ActivityMain : AppCompatActivity() {

    private lateinit var btnCheckBoxes : MaterialButton
    private lateinit var btnRadioButtons : MaterialButton
    private lateinit var btnSpinner : MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCheckBoxes = findViewById(R.id.btnCheckBoxes)
        btnRadioButtons = findViewById(R.id.btnRadioButtons)
        btnSpinner = findViewById(R.id.btnSpinner)

        btnCheckBoxes.setOnClickListener {
            Toast.makeText(this, "Pulsado CB", Toast.LENGTH_SHORT).show()
            Log.d("Mi app", "Pulsado CB")
            startActivity(Intent(this, ActivityCheckBoxes::class.java))
        }

        btnRadioButtons.setOnClickListener {
            Toast.makeText(this, "Pulsado RB", Toast.LENGTH_SHORT).show()
            Log.d("Mi app", "Pulsado RB")
            startActivity(Intent(this, ActivityRadioButtons::class.java))
        }

        btnSpinner.setOnClickListener {
            Toast.makeText(this, "Pulsado SP", Toast.LENGTH_SHORT).show()
            Log.d("Mi app", "Pulsado SP")
            startActivity(Intent(this, ActivitySpinner::class.java))
        }

    }
}