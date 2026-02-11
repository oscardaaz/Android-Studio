package com.example.ejercicio.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ejercicio.R
import com.example.ejercicio.data.dao.RecetaDAOImpl
import com.example.ejercicio.data.database.RecetasSQLiteHelper
import com.example.ejercicio.data.model.Receta
import com.example.ejercicio.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var operaciones : RecetaDAOImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val toolbar = binding.cbMaterialToolbar
//
//        toolbar.inflateMenu(R.menu.menu_appbar)

        //Inicializamos el DAO
        val dbHelper = RecetasSQLiteHelper(this)
        operaciones = RecetaDAOImpl(dbHelper)

        toolbar()
        configurarListeners()


    }

    private fun configurarListeners() {
        binding.btnINS.setOnClickListener { manejarInsercion() }
        binding.btnDEL.setOnClickListener { manejarEliminacion() }
        binding.btnCON.setOnClickListener { manejarConsulta() }

    }

    private fun manejarConsulta() {
        TODO("Not yet implemented")
    }

    private fun manejarEliminacion() {
        TODO("Not yet implemented")
    }

    private fun manejarInsercion() {

        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val categoria = binding.textInputLayoutCategoria.editText?.text.toString().trim()
        val tiempo = binding.textInputLayoutTiempo.editText?.text.toString().trim()

        if (nombre.isEmpty() || categoria.isEmpty() || tiempo.isEmpty()){
            mostrarMensaje("Completa los campos obligatorios")
            binding.textInputLayoutNombre.error = "Campo Obligatorio"
            binding.textInputLayoutCategoria.error = "Campo Obligatorio"
            binding.textInputLayoutTiempo.error = "Campo Obligatorio"
            return
        }

        val receta = Receta(
            nombre = nombre,
            categoria = categoria,
            tiempo_preparacion = tiempo.toInt()
        )

        val idGenerado = operaciones.insertarReceta(receta)
        if (idGenerado != -1L) {
            mostrarMensaje("Receta insertada con id: $idGenerado")
        } else {
            mostrarMensaje("Error al insertar receta")
        }

        limpiarErrores()
        limpiarCampos()
        limpiarFocus()
        actualizarListaUsuarios()

    }

    private fun limpiarErrores(){
        binding.textInputLayoutNombre.error = null
        binding.textInputLayoutCategoria.error = null
        binding.textInputLayoutTiempo.error = null
    }

    private fun mostrarMensaje(mensaje: String) {
        Snackbar.make(
            binding.root,
            mensaje,
            Snackbar.LENGTH_LONG
        ).show()
    }
    private fun limpiarCampos() {
        binding.textInputLayoutID.editText?.setText("")
        binding.textInputLayoutNombre.editText?.setText("")
        binding.textInputLayoutCategoria.editText?.setText("")
        binding.textInputLayoutTiempo.editText?.setText("")
    }
    private fun limpiarFocus(){
        binding.textInputLayoutID.clearFocus()
        binding.textInputLayoutNombre.clearFocus()
        binding.textInputLayoutCategoria.clearFocus()
        binding.textInputLayoutTiempo.clearFocus()
    }
    private fun toolbar() {
        val toolbar = binding.cbMaterialToolbar
        toolbar.inflateMenu(R.menu.menu_appbar)

        toolbar.setOnMenuItemClickListener { item ->

            when (item.itemId){
                R.id.action_insertar -> {
                    manejarInsercion()
                    true
                }
                R.id.action_consultar -> {
                    manejarConsulta()
                    true
                }
                R.id.action_eliminar -> {
                    manejarEliminacion()
                    true
                }

                else -> false
            }

        }

    }


    private fun actualizarListaUsuarios() {
        val recetas = operaciones.leerRecetas()
        if (recetas.isEmpty()) {
            binding.tvOutput.text = "Tabla vacía, No existen Usuarios"
        } else {
            val textoTabla = StringBuilder()
            for (receta in recetas) {
                textoTabla.append("$receta\n")
            }
            binding.tvOutput.text = textoTabla.toString()
        }

    }

} // FIn de la clase