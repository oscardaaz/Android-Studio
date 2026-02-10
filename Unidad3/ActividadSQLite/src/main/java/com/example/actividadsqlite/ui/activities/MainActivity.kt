package com.example.actividadsqlite.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadsqlite.data.dao.UsuarioDAOImpl
import com.example.actividadsqlite.data.database.UsuariosSQLiteHelper
import com.example.actividadsqlite.data.model.Usuario
import com.example.actividadsqlite.databinding.ActivityMainBinding
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
        binding.btnPapelera.setOnClickListener { borrarTodosLosUsuarios() }
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
       // override fun contarUsuarios(): Int {
//        val cantidadUsuarios = operaciones.contarUsuarios()
//        if (cantidadUsuarios == 0) {
//            mostrarMensaje("Mostrando usuarios totales")
//            val intent = Intent(this, SecondActivity::class.java)
//            intent.putExtra("DATOS", "No hay usuarios en la lista")
//            startActivity(intent)
//            return
//        }else{
//            mostrarMensaje("Mostrando usuarios totales")
//            val intent = Intent(this, SecondActivity::class.java)
//            intent.putExtra("DATOS", "La cantidad total de usuarios es: ${cantidadUsuarios.toString()}")
//            startActivity(intent)
//            return
//        }
    }

    private fun actualizarListaUsuarios() {
        val usuarios = operaciones.leerUsuariosOrdenadosPorNombre()
        if (usuarios.isEmpty()) {
            binding.tvOutput.text = "Tabla vacía, No existen Usuarios"
        } else {
            val textoTabla = StringBuilder()
            for (usuario in usuarios) {
                textoTabla.append("$usuario\n")
            }
            binding.tvOutput.text = textoTabla.toString()
        }

    }

    private fun manejarEliminacion() {
        val dominio = binding.textInputLayoutEmail.editText?.text.toString().trim()

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

        val filasEliminadas = operaciones.borrarUsuariosPorDominio(dominio)
        mostrarMensaje("Eliminacion correcta")
        actualizarListaUsuarios()
    }

    private fun borrarTodosLosUsuarios() {
        val filasEliminadas = operaciones.borrarTodosLosUsuarios()
        if (filasEliminadas > 0) {
            mostrarMensaje("Eliminación correcta"); limpiarCampos(); actualizarListaUsuarios()
        } else {
            mostrarMensaje("No se ha podido eliminar")
        }
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
        // Actualizar nombre por id
//        operaciones.actualizarNombre(id,nombre)
//        actualizarListaUsuarios()

        //Actualizar email si no existe otro email igual
       val actualizarEmail = operaciones.actualizarEmailSiDisponible(19,"nuevoEemail.com")
        if (actualizarEmail){
            mostrarMensaje("El usuario ha sido actualizado correctamente")
            actualizarListaUsuarios()
        }else {
            mostrarMensaje("No se ha podido actualizar ese usuario")
        }

    }

    private fun manejarInsercion() {
        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()

        if (operaciones.existeUsuario(email)) {
            mostrarMensaje("El usuario ya esta registrado con el email: $email")
            return
        }

        if (nombre.isEmpty() || email.isEmpty() || nombre == "" || email == "") {
            mostrarMensaje("Completa los campos nombre & email")
            if (nombre.isEmpty()) {
                binding.textInputLayoutNombre.error = "*Nombre Obligatorio*"
            }
            if (email.isEmpty()){
                binding.textInputLayoutEmail.error = "*Email Obligatorio*"
            }
            return
        }

        val usuario = Usuario(
            nombre = nombre,
            email = email
        )
        val idGenerado = operaciones.insertarUsuarioSiEmailNoExiste(usuario)

        if (idGenerado != -1L) {
            mostrarMensaje("Usuario insertado con id: $idGenerado")
            binding.textInputLayoutEmail.error = ""
            binding.textInputLayoutNombre.error = ""
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







