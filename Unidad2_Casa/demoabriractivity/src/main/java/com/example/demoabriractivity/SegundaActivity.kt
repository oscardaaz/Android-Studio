package com.example.demoabriractivity

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SegundaActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var tvTelefono: TextView
    private lateinit var tvEmail: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda)

        tvUsername = findViewById(R.id.tvUsername)
        tvTelefono = findViewById(R.id.tvTelefono)
        tvEmail = findViewById(R.id.tvEmail)

        // Recibimos los datos del intent y los mostramos en los TextView
//        tvUsername.text = intent.getStringExtra("USERNAME") ?: "No proporcionado"
//        tvTelefono.text = intent.getStringExtra("TELEFONO") ?: "No proporcionado"
//        tvEmail.text = intent.getStringExtra("EMAIL") ?: "No proporcionado"

        val usuarioRecibido = if  (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("OBJETO_USUARIO",Usuario::class.java)
        }else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Usuario>("OBJETO_USUARIO")
        }
        tvUsername.text = usuarioRecibido?.username
        tvTelefono.text = usuarioRecibido?.telefono
        tvEmail.text = usuarioRecibido?.email

    }
}