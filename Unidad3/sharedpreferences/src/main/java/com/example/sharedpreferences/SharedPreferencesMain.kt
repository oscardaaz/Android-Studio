package com.example.sharedpreferences

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sharedpreferences.databinding.ActivitySharedPreferencesMainBinding

class SharedPreferencesMain : AppCompatActivity() {

    private lateinit var binding: ActivitySharedPreferencesMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPreferencesMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.textView2.text = "Esto es la prueba de que funciona o quien sabe que demonios estoy haciendo"

        binding.button2.setOnClickListener {

            startActivity(Intent(this, AjustesActivity::class.java))
        }
    }
}