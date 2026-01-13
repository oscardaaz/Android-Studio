package com.example.sharedpreferences

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sharedpreferences.databinding.ActivityAjustesBinding

class AjustesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAjustesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAjustesBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Leer el fichero de preferencias
        val prefs = getSharedPreferences("MisPrefs", Context.MODE_PRIVATE)

        binding.switch2.isChecked = prefs.getBoolean("oscuro", false)
        binding.editTextText.setText(prefs.getInt("tam_letra", 12).toString())

        setContentView(binding.root)

        binding.button.setOnClickListener {
            val prefs = getSharedPreferences("MisPrefs", Context.MODE_PRIVATE).edit()
            prefs.putBoolean("oscuro", binding.switch2.isChecked)
            prefs.putInt("tam_letra", binding.editTextText.toString().toInt())
            prefs.apply()
        }
        finish()
    }
}