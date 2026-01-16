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
        //enableEdgeToEdge()
        setContentView(binding.root)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        //TODO Arreglar algo aqui, explota al ir a la segunda actividad.
        binding.button2.setOnClickListener {
            startActivity(Intent(
                this ,AjustesActivity::class.java))
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
        val tamLetra = prefs.getInt("tam_letra", 12)

        binding.texto.textSize = tamLetra.toFloat()
        if (oscuro) {
            // Establecemos el modo noche en la aplicación
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // Establecemos el modo día en la aplicación
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


}