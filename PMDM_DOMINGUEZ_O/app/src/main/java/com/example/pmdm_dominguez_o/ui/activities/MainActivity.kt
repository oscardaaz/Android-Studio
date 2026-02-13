package com.example.pmdm_dominguez_o.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.pmdm_dominguez_o.R
import com.example.pmdm_dominguez_o.data.dao.RecetaDAOImpl
import com.example.pmdm_dominguez_o.data.database.RecetaSQLiteHelper
import com.example.pmdm_dominguez_o.data.model.Receta
import com.example.pmdm_dominguez_o.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var operaciones: RecetaDAOImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.cbMaterialToolbar

        toolbar.inflateMenu(R.menu.menu_appbar)

        val dbHelper = RecetaSQLiteHelper(this)
        operaciones = RecetaDAOImpl(dbHelper)

        configurarListeners()
        configurarToolbar()
        actualizarListaRecetas()

    }

    private fun actualizarListaRecetas() {
        val recetas = operaciones.consultarRecetas()
        if (recetas.isEmpty()) {
            binding.tvOutput.text = "Tabla vacía, No existen Recetas"
        } else {
            val textoTabla = StringBuilder()
            for (receta in recetas) {
                textoTabla.append("$receta\n\n")
            }
            binding.tvOutput.text = textoTabla.toString()
        }
    }

    private fun configurarToolbar() {
        binding.cbMaterialToolbar.setOnMenuItemClickListener { item ->

            when (item.itemId) {
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

    private fun configurarListeners() {
        binding.btnINS.setOnClickListener { manejarInsercion() }
        binding.btnDEL.setOnClickListener { manejarEliminacion() }
        binding.btnCON.setOnClickListener { manejarConsulta() }

    }

    private fun manejarConsulta() {
        limpiarErrores()
        actualizarListaRecetas()
    }

    private fun manejarEliminacion() {
        limpiarErrores()
        val idTexto = binding.textInputLayoutID.editText?.text.toString().trim()

        // Comprobar si esta vacio
        if (idTexto.isEmpty()) {
            mostrarMensaje("Introduce id para eliminar")
            binding.textInputLayoutID.error = "Este campo no puede estar vacio"
            return
        }

        if (idTexto.toIntOrNull() == null) {
            mostrarMensaje("El ID tiene que ser un numero")
            binding.textInputLayoutID.error = "El ID tiene que ser un numero"
            return
        }

        val filasEliminadas = operaciones.borrarPorId(idTexto.toInt())

        if (filasEliminadas > 0) {
            mostrarMensaje("Receta $idTexto borrada correctamente")
        } else {
            Toast.makeText(this, "La receta con ID: $idTexto no existe", Toast.LENGTH_LONG).show()
            Log.d("MainActivity", "El id: $idTexto no existe")
        }


        limpiarCampos()
        limpiarErrores()
        limpiarFocus()
    }

    private fun manejarInsercion() {
        limpiarErrores()
        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val categoria = binding.textInputLayoutCategoria.editText?.text.toString().trim()
        val tiempo = binding.textInputLayoutTiempo.editText?.text.toString().trim()

        if (nombre.isEmpty() || categoria.isEmpty() || tiempo.isEmpty()) {
            mostrarMensaje("Completa los campos obligatorios")
            if (nombre.isEmpty()) binding.textInputLayoutNombre.error = "*Nombre Obligatorio*"
            if (categoria.isEmpty()) binding.textInputLayoutCategoria.error =
                "*Categoria obligatoria"
            if (tiempo.isEmpty()) binding.textInputLayoutTiempo.error = "*Tiempo obligatorio"
            return
        }

        if (tiempo.toIntOrNull() == null) {
            mostrarMensaje("*El tiempo tiene que ser un numero")
            binding.textInputLayoutTiempo.error = "*El tiempo tiene que ser un numero"
            return
        }

        if (tiempo.toInt() <= 0) {
            mostrarMensaje("*El tiempo tiene que ser mayor de 0")
            binding.textInputLayoutTiempo.error = "*El tiempo tiene que ser mayor de 0 minutos"
            return
        }

        val receta = Receta(
            name = nombre,
            category = categoria,
            preparation_time = tiempo.toInt()
        )
        val idGenerado = operaciones.insertarReceta(receta)

        if (idGenerado != -1L) {
            mostrarMensaje("Receta $idGenerado insertada correctamente")
        } else {
            mostrarMensaje("Error al insertar")
        }

        limpiarCampos()
        limpiarFocus()
    }

    private fun limpiarCampos() {
        binding.textInputLayoutID.editText?.setText("")
        binding.textInputLayoutNombre.editText?.setText("")
        binding.textInputLayoutCategoria.editText?.setText("")
        binding.textInputLayoutTiempo.editText?.setText("")
    }

    private fun limpiarErrores() {
        binding.textInputLayoutID.error = null
        binding.textInputLayoutNombre.error = null
        binding.textInputLayoutCategoria.error = null
        binding.textInputLayoutTiempo.error = null
    }

    private fun limpiarFocus() {
        binding.textInputLayoutID.clearFocus()
        binding.textInputLayoutNombre.clearFocus()
        binding.textInputLayoutCategoria.clearFocus()
        binding.textInputLayoutTiempo.clearFocus()
    }

    private fun mostrarMensaje(mensaje: String) {
        Snackbar.make(
            binding.root,
            mensaje,
            Snackbar.LENGTH_LONG
        ).show()
    }
}