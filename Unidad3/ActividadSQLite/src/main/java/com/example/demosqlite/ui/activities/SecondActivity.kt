package com.example.demosqlite.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demosqlite.R
import com.example.demosqlite.data.dao.UsuarioDAOImpl
import com.example.demosqlite.databinding.ActivitySecondBinding
import com.google.android.material.appbar.MaterialToolbar

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    private lateinit var operaciones: UsuarioDAOImpl

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_second)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val toolbar = findViewById<MaterialToolbar>(R.id.cbMaterialToolbar)
        val toolbar = binding.cbMaterialToolbar

        toolbar.setNavigationOnClickListener {
            //finish()
            onBackPressedDispatcher.onBackPressed()
        }
        //Se puede poner aqui o en el XML
//        toolbar.title = "Lista de Usuarios"
//        toolbar.subtitle = "Programacion Moviles"


        val datos = intent.getStringExtra("DATOS")
        binding.textView.text = datos

    }

}