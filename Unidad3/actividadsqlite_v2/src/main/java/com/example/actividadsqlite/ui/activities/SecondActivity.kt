package com.example.actividadsqlite.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadsqlite.data.dao.UsuarioDAOImpl
import com.example.actividadsqlite.databinding.ActivitySecondBinding

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