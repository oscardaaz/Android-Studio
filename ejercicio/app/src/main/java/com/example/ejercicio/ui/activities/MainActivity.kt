package com.example.ejercicio.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
       // actualizarListaUsuarios()


    }

    private fun configurarListeners() {
        binding.btnINS.setOnClickListener { manejarInsercion() }
        binding.btnDEL.setOnClickListener { manejarEliminacion() }
        binding.btnCON.setOnClickListener { manejarConsultaSegundaActivity() }

    }

    private fun manejarConsulta() {
        limpiarErrores()
        limpiarCampos()
        limpiarFocus()
        actualizarListaReceta()
    }

    private fun manejarConsultaSegundaActivity(){
        val recetas = operaciones.leerRecetas()

        val datos = if (recetas.isEmpty()) {
            "Tabla vacía, No existen recetas"
        } else {
            val textoTabla = StringBuilder()
            for (receta in recetas) {
                textoTabla.append("$receta\n\n")
            }
         textoTabla.toString()
        }

        val intent = Intent(this, SegundaActivity::class.java)
        intent.putExtra("DATOS",datos)
        startActivity(intent)

    }

    private fun manejarEliminacion() {

        limpiarErrores()
        val id = binding.textInputLayoutID.editText?.text.toString().trim()

        if (id.isEmpty()) {
            mostrarMensaje("El campo ID es obligatorio para eliminar")
            binding.textInputLayoutID.error = "*Campo obligatorio para eliminar"
            return
        }

        val operacion = operaciones.borrarReceta(id.toInt())

        if (operacion > 0){
            mostrarMensaje("Elminacion del usuario con id: $id correcta")
        }else{
            mostrarMensaje("No se ha podido eliminar el usuario con id: $id")
            Toast.makeText(this,"Error al insertar usuario " +
                    "con id: $id",Toast.LENGTH_LONG).show()
            Log.d("MainActivity","El id: $id no existe")
            Log.e("MainActivity","El id: $id no existe")
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

        if (nombre.isEmpty() || categoria.isEmpty() || tiempo.isEmpty()){
            mostrarMensaje("Completa los campos obligatorios para insertar")
            binding.textInputLayoutNombre.error = "Campo Obligatorio para insertar"
            binding.textInputLayoutCategoria.error = "Campo Obligatorio para insertar"
            binding.textInputLayoutTiempo.error = "Campo Obligatorio para insertar"
            return
        }

        if (tiempo.toIntOrNull() == null || tiempo.toInt() > 0){
            mostrarMensaje("El tiempo tiene que ser un numero y mayor de 0")
            binding.textInputLayoutTiempo.error = "El tiempo tiene que ser un numero y mayor de 0"
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
        actualizarListaReceta()

    }

    private fun limpiarErrores(){
        binding.textInputLayoutID.error = null
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


    private fun actualizarListaReceta() {
        val recetas = operaciones.leerRecetas()
        if (recetas.isEmpty()) {
            binding.tvOutput.text = "Tabla vacía, No existen recetas"
        } else {
            val textoTabla = StringBuilder()
            for (receta in recetas) {
                textoTabla.append("$receta\n\n")
            }
            binding.tvOutput.text = textoTabla.toString()
        }

    }

} // FIn de la clase