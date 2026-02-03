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
        val idEmail = binding.textInputLayoutEmail.editText?.text.toString().trim()

        //CASO 1
        if (idEmail.isEmpty()) {

            val usuarios = operaciones.leerUsuariosOrdenadosPorNombre()
            actualizarListaUsuarios()

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
        if (!idEmail.isEmpty()) {

            if (operaciones.existeUsuario(idEmail)) {
                val usuario = operaciones.leerUsuarioPorEmail(idEmail).toString()

                mostrarMensaje("Mostrando usuario con email: $idEmail")
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("DATOS", usuario)
                startActivity(intent)
                return
            }else{
                mostrarMensaje("El usuario con id: $idEmail ,no existe")
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("DATOS", "El usuario con email: $idEmail ,no existe")
                startActivity(intent)
                return
            }


        }


        //CASO 2
    }

    private fun actualizarListaUsuarios() {
        val usuarios = operaciones.leerUsuariosOrdenadosPorNombre()
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
        val idTexto = binding.textInputLayoutEmail.editText?.text.toString().trim()

//        // Comprobar si esta vacio
//        if (idTexto.isEmpty()) {
//            mostrarMensaje("Introduce id para eliminar")
//            return
//        }
//
//        // Si no esta vacio, comprobamos que sea un número valido.
//        val email = idTexto
//        if (email == null) {
//            mostrarMensaje("Iemail tiene que ser un nº ")
//            return
//        }
//
//        val filasEliminadas = operaciones.borrarUsuario(email)
//        if (filasEliminadas > 0) {
//            mostrarMensaje("Eliminación correcta"); limpiarCampos(); actualizarListaUsuarios()
//        } else {
//            mostrarMensaje("No se ha podido eliminar")
//        }
        operaciones.borrarUsuariosPorDominio(idTexto)
        actualizarListaUsuarios()
    }

    private fun manejarActualizacion() {
        val idTexto = binding.textInputLayoutID.editText?.text.toString().trim()
        val id = idTexto.toInt()
        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
//
//        // Comprobar si esta vacio
//        if (idTexto.isEmpty()) {
//            mostrarMensaje("Introduce id para actualizar")
//            return
//        }
//
//        // Si no esta vacio, comprobamos que sea un número valido.
//        val id = idTexto.toIntOrNull()
//        if (id == null) {
//            mostrarMensaje("ID tiene que ser un nº ")
//            return
//        }
//        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
//        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()
//
//        // TODO: Validar campos nombre e email.
//        val usuarioActualizado = Usuario(
//            id = id,
//            nombre,
//            email = email
//        )
//        val filasActualizadas = operaciones.actualizarUsuario(usuarioActualizado)
//        if (filasActualizadas > 0) {
//            mostrarMensaje("Actualizacion correcta"); limpiarCampos(); actualizarListaUsuarios()
//        } else {
//            mostrarMensaje("No se ha podido actualizar")
//        }

        operaciones.actualizarNombre(id,nombre)
        actualizarListaUsuarios()

    }

    private fun manejarInsercion() {
        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()

        if (operaciones.existeUsuario(email)) {
            mostrarMensaje("El usuario ya esta registrado con el email: $email")
            return
        }

        if (nombre.isEmpty() || email.isEmpty()) {
            mostrarMensaje("Completa los campos nombre & email")
            return
        }

        val usuario = Usuario(
            nombre = nombre,
            email = email
        )
        val idGenerado = operaciones.insertarUsuarioSiEmailNoExiste(usuario)

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





