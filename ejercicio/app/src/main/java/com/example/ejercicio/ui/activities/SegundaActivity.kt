package com.example.ejercicio.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejercicio.R
import com.example.ejercicio.data.dao.RecetaDAOImpl
import com.example.ejercicio.data.database.RecetasSQLiteHelper
import com.example.ejercicio.databinding.ActivitySegundaBinding

class SegundaActivity : AppCompatActivity() {


   // private lateinit var operaciones : RecetaDAOImpl
    private lateinit var binding : ActivitySegundaBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        // PRIMERO ESTO SIEMPRE
        binding = ActivitySegundaBinding.inflate(layoutInflater)

        //Despues del CONTENTVIEW SIEMPRE SINO ERROR NO INICIALIZADO BINDING
        setContentView(binding.root)
        val toolbar = binding.cbMaterialToolbar

        toolbar.setNavigationOnClickListener {
            //finish()
            onBackPressedDispatcher.onBackPressed()
        }


//        val dbHelper = RecetasSQLiteHelper(this)
//        operaciones = RecetaDAOImpl(dbHelper)

        val datos = intent.getStringExtra("DATOS")
        binding.tvOutput.text = datos.toString()

       // val tabla = operaciones.leerRecetas()

//        val datos = StringBuilder()
//        for (tablas in tabla){
//           datos.append("$tablas\n\n")
//
//       }
//        binding.tvOutput.text = datos

    }
}