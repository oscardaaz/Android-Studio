package com.example.demosqlite.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demosqlite.data.dao.UsuarioDAOImpl
import com.example.demosqlite.data.database.UsuariosSQLiteHelper
import com.example.demosqlite.data.model.Usuario
import com.example.demosqlite.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var operaciones: UsuarioDAOImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializamos el DAO
        val dbHelper = UsuariosSQLiteHelper(this)
        operaciones = UsuarioDAOImpl(dbHelper)

        configurarListeners()

        //actualizarListaUsuarios()

    } // Fin del onCreate


    private fun configurarListeners() {
        binding.btnINS.setOnClickListener { manejarInsercion() }
        binding.btnACT.setOnClickListener { manejarActualizacion() }
        binding.btnDEL.setOnClickListener { manejarEliminacion() }
        binding.btnCON.setOnClickListener { manejarConsulta() }

    }

    private fun manejarConsulta() {
        val idTexto = binding.textInputLayoutNombre.editText?.text.toString().trim()

        //CASO 1
        if (idTexto.isEmpty()) {
            actualizarListaUsuarios()
            mostrarMensaje("Mostrando todos los usuarios....")
            return
        }

        //CASO 2
    }

    private fun actualizarListaUsuarios() {
        val usuarios = operaciones.leerUsuarios()
        if (usuarios.isEmpty()) {
            binding.tvOutput.text = "Tabla vacía"
        } else {
            val textoTabla = StringBuilder()
            for (usuario in usuarios){
                textoTabla.append("$usuario\n")
            }
            binding.tvOutput.text = textoTabla.toString()
        }


    }

    private fun manejarEliminacion() {
        TODO("Not yet implemented")
    }

    private fun manejarActualizacion() {
        TODO("Not yet implemented")
    }

    private fun manejarInsercion() {
        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val email = binding.textInputLayoutNombre.editText?.text.toString().trim()

        if (nombre.isEmpty() || email.isEmpty()){
            mostrarMensaje("Completa los campos nombre & email")
            return
        }
        val usuario = Usuario(
            nombre = nombre,
            email = email
        )
        val idGenerado = operaciones.insertarUsuario(usuario)

        if (idGenerado != -1L) {
            mostrarMensaje("Usuario insertado con id: $idGenerado")
            limpiarCampos()
            //actualizarListaUsuarios();
        } else {
            mostrarMensaje("Error al insertar usuario")
            limpiarCampos()
        }

    }

    private fun limpiarCampos() {
        binding.textInputLayoutID.editText?.setText("")
        binding.textInputLayoutNombre.editText?.setText("")
        binding.textInputLayoutEmail.editText?.setText("")
    }

    private fun mostrarMensaje(mensaje: String) {
        Snackbar.make(
            binding.root,
            mensaje,
            Snackbar.LENGTH_LONG
        ).show()
    }

} // Fin de la clase



