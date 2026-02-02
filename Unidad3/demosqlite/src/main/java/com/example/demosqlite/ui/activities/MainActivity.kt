package com.example.demosqlite.ui.activities

import android.content.Intent
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

        actualizarListaUsuarios()

    } // Fin del onCreate


    private fun configurarListeners() {
        binding.btnINS.setOnClickListener { manejarInsercion() }
        binding.btnACT.setOnClickListener { manejarActualizacion() }
        binding.btnDEL.setOnClickListener { manejarEliminacion() }
        binding.btnCON.setOnClickListener { manejarConsulta() }

    }

    private fun manejarConsulta() {
        val idTexto = binding.textInputLayoutID.editText?.text.toString().trim()

        //CASO 1
        if (idTexto.isEmpty()) {

            val usuarios = operaciones.leerUsuarios()

            val texto = if (usuarios.isEmpty()) {
                "No hay usuarios"
            } else {
                val textoTabla = StringBuilder()
                for (usuario in usuarios) {
                    textoTabla.append("$usuario\n")

                }
                textoTabla.toString()
            }

            mostrarMensaje("Mostrando usuarios")
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("DATOS", texto)
            startActivity(intent)
            return

        }

        // CASO 2, id introducido.
        if (!idTexto.isEmpty()) {

            val numero = idTexto.toInt()
            val usuario = operaciones.leerUsuarioPorId(numero).toString()

            mostrarMensaje("Mostrando usuario con id: $idTexto")
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("DATOS", usuario)
            startActivity(intent)
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
            for (usuario in usuarios) {
                textoTabla.append("$usuario\n")
            }
            binding.tvOutput.text = textoTabla.toString()
        }


    }

    private fun manejarEliminacion() {
        val idTexto = binding.textInputLayoutID.editText?.text.toString().trim()

        // Comprobar si esta vacio
        if (idTexto.isEmpty()) {
            mostrarMensaje("Introduce id para eliminar")
            return
        }

        // Si no esta vacio, comprobamos que sea un número valido.
        val id = idTexto.toIntOrNull()
        if (id == null) {
            mostrarMensaje("ID tiene que ser un nº ")
            return
        }

        val filasEliminadas = operaciones.borrarUsuario(id)
        if (filasEliminadas > 0) {
            mostrarMensaje("Eliminación correcta"); limpiarCampos(); actualizarListaUsuarios()
        } else {
            mostrarMensaje("No se ha podido eliminar")
        }
    }

    private fun manejarActualizacion() {
        val idTexto = binding.textInputLayoutID.editText?.text.toString().trim()

        // Comprobar si esta vacio
        if (idTexto.isEmpty()) {
            mostrarMensaje("Introduce id para actualizar")
            return
        }

        // Si no esta vacio, comprobamos que sea un número valido.
        val id = idTexto.toIntOrNull()
        if (id == null) {
            mostrarMensaje("ID tiene que ser un nº ")
            return
        }
        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()

        // TODO: Validar campos nombre e email.
        val usuarioActualizado = Usuario(
            id = id,
            nombre,
            email = email
        )
        val filasActualizadas = operaciones.actualizarUsuario(usuarioActualizado)
        if (filasActualizadas > 0) {
            mostrarMensaje("Actualizacion correcta"); limpiarCampos(); actualizarListaUsuarios()
        } else {
            mostrarMensaje("No se ha podido actualizar")
        }


    }

    private fun manejarInsercion() {
        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()

        if (nombre.isEmpty() || email.isEmpty()) {
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
            actualizarListaUsuarios()
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





