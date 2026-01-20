package com.example.sharedpreferences

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sharedpreferences.databinding.ActivitySharedPreferencesMainBinding

class SharedPreferencesMain : AppCompatActivity() {

    private lateinit var binding: ActivitySharedPreferencesMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Miapp","onCreate")
        binding = ActivitySharedPreferencesMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // CORRECCIÓN: Agregar leerPreferencias() aquí también para aplicar los cambios al iniciar
        //leerPreferencias()

        binding.button2.setOnClickListener {
            startActivity(Intent(this, AjustesActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("Miapp","onResume")
        leerPreferencias()
    }

    private fun leerPreferencias() {
        val prefs = getSharedPreferences("MisPrefs", Context.MODE_PRIVATE)
        val oscuro = prefs.getBoolean("oscuro", false)
        val tamLetra = prefs.getInt("tam_letra", 12).toString()

        binding.texto.textSize = tamLetra.toFloat()

        // Aplicar el tema inmediatamente
        if (oscuro) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}