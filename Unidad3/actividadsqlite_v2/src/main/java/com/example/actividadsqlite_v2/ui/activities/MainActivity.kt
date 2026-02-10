package com.example.actividadsqlite_v2.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadsqlite_v2.data.dao.UsuarioDAOImpl
import com.example.actividadsqlite_v2.data.database.UsuariosSQLiteHelper
import com.example.actividadsqlite_v2.data.model.Usuario
import com.example.actividadsqlite_v2.databinding.ActivityMainBinding
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
        binding.btnPapelera.setOnClickListener { manejarEliminacionTotal() }

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
            if (operaciones.existeUsuario(numero)) {
                val usuario = operaciones.leerUsuarioPorId(numero).toString()

                mostrarMensaje("Mostrando usuario con id: $idTexto")
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("DATOS", usuario)
                startActivity(intent)
                return
            }else{
                mostrarMensaje("El usuario con id: $idTexto ,no existe")
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("DATOS", "El usuario con id: $idTexto ,no existe")
                startActivity(intent)
                return
            }


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
            mostrarMensaje("Introduce un id para eliminar")
            binding.textInputLayoutID.error = "El campo ID debe ser introducido"
            return
        }

        // Si no esta vacio, comprobamos que sea un número valido.
        val id = idTexto.toIntOrNull()
        if (id == null) {
            mostrarMensaje("ID tiene que ser un nº ")
            binding.textInputLayoutID.error = "El ID tiene que ser un nº obligatoriamente"
            return
        }

        val filasEliminadas = operaciones.borrarUsuario(id)
        if (filasEliminadas > 0) {
            mostrarMensaje("Eliminación correcta del usuario con" +
                    "id: $id"); limpiarCampos(); actualizarListaUsuarios()
            binding.textInputLayoutID.error = ""
        } else {
            mostrarMensaje("No se ha podido eliminar")
            binding.textInputLayoutID.error = ""
        }
    }

    private fun manejarEliminacionTotal() {
       val filasEliminadas = operaciones.borrarTodosLosUsuarios()
        if  (filasEliminadas > 0){
            mostrarMensaje("Eliminados $filasEliminadas usuarios")
            actualizarListaUsuarios()
        }else {
            mostrarMensaje("No se han podido eliminar los usuarios," +
                    " no existian usuarios")
        }
    }

    private fun manejarActualizacion() {
        val idTexto = binding.textInputLayoutID.editText?.text.toString().trim()
        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()
        val validarEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        // Comprobar si esta vacio
        if (idTexto.isEmpty()) {
            mostrarMensaje("Introduce id para actualizar")
            binding.textInputLayoutID.error = "El campo ID debe ser introducido"
            return
        }
        if (!idTexto.isEmpty()) {
            binding.textInputLayoutID.error = ""

        }


        // Si no esta vacio, comprobamos que sea un número valido.
        val id = idTexto.toIntOrNull()
        if (id == null) {
            mostrarMensaje("ID tiene que ser un nº ")
            binding.textInputLayoutID.error = "El ID tiene que ser un nº obligatoriamente"
            return
        }

        if (email.isEmpty() || nombre.isEmpty()) {
            mostrarMensaje("Campos email y nombre obligatorios")
            if (nombre.isEmpty()) binding.textInputLayoutNombre.error = "Campo obligatorio para actualizar*"
            if (!nombre.isEmpty()) limpiarErrores(NOMBRE)
            if (email.isEmpty()) binding.textInputLayoutEmail.error = "Campo obligatorio para actualizar*"
            if (!email.isEmpty()) limpiarErrores(EMAIL)
            return
        }
        if (!validarEmail) {
            mostrarMensaje("El email no tiene el formato correcto")
            binding.textInputLayoutEmail.error = "Estructura no valida, prueba: 'email@dominio.com' "
            return
        }

//        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
//        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()

        // TODO: Validar campos nombre e email.
        val usuarioActualizado = Usuario(
            id = id,
            nombre = nombre,
            email = email
        )
        val filasActualizadas = operaciones.actualizarUsuario(usuarioActualizado)
        if (filasActualizadas > 0) {
            mostrarMensaje(
                "Actualizacion correcta del usuario" +
                        " con id: $id"
            ); actualizarListaUsuarios()

        } else {
            mostrarMensaje("No se ha podido actualizar")
        }
        limpiarCampos()
        limpiarErrores(TODOS)

        limpiarFocus()
    }

    private fun manejarInsercion() {


        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()
        val validarEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (nombre.isEmpty() || email.isEmpty()) {
            mostrarMensaje("Campos email y nombre obligatorios")
            if (nombre.isEmpty()) binding.textInputLayoutNombre.error = "Campo obligatorio para actualizar*"
            if (!nombre.isEmpty()) limpiarErrores(NOMBRE)
            if (email.isEmpty()) binding.textInputLayoutEmail.error = "Campo obligatorio para actualizar*"
            if (!email.isEmpty()) limpiarErrores(EMAIL)
            return
        }
        if (!validarEmail) {
            mostrarMensaje("El email no tiene el formato correcto")
            binding.textInputLayoutEmail.error = "Estructura no valida, prueba: 'email@dominio.com' "
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
            mostrarMensaje("Error al insertar usuario $idGenerado")
            limpiarCampos()
        }
        limpiarErrores(TODOS)
        limpiarFocus()
    }

    private fun limpiarCampos() {
        binding.textInputLayoutID.editText?.setText("")
        binding.textInputLayoutNombre.editText?.setText("")
        binding.textInputLayoutEmail.editText?.setText("")
        // Otra opcion en vez con setText con text.clear()
    //        binding.textInputLayoutEmail.editText?.text?.clear()
    }

    private fun mostrarMensaje(mensaje: String) {
        Snackbar.make(
            binding.root,
            mensaje,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun limpiarFocus(){
        binding.textInputLayoutID.clearFocus()
        binding.textInputLayoutNombre.clearFocus()
        binding.textInputLayoutEmail.clearFocus()
    }

    // 2. Nueva función para limpiar focus con el companion object tambien
//    private fun limpiarFocus(vararg campos: String) {
//        if (campos.isEmpty()) {
//            // Si no hay parámetros, limpia TODOs el focus (comportamiento original)
//            binding.textInputLayoutID.clearFocus()
//            binding.textInputLayoutNombre.clearFocus()
//            binding.textInputLayoutEmail.clearFocus()
//            return
//        }
//
//        if (campos.size == 1 && campos[0] == TODOS) {
//            // Con "all" también limpia TODO
//            binding.textInputLayoutID.clearFocus()
//            binding.textInputLayoutNombre.clearFocus()
//            binding.textInputLayoutEmail.clearFocus()
//            return
//        }
//
//        // Limpia solo los campos específicos
//        campos.forEach { campo ->
//            when (campo.trim().lowercase()) {
//                NOMBRE -> binding.textInputLayoutNombre.clearFocus()
//                ID -> binding.textInputLayoutID.clearFocus()
//                EMAIL -> binding.textInputLayoutEmail.clearFocus()
//            }
//        }
//    }


    // Funcion original de limpiar errores.
//    private fun limpiarErrores(){
//        binding.textInputLayoutID.error = null
//        binding.textInputLayoutNombre.error = null
//        binding.textInputLayoutEmail.error = null
//    }

    // Varias pruebas de funciones con diversas maneras solo por curiosidad.

    // Con String y default "all" -> ()
//    private fun limpiarErrores(campo: String = "all") {
//        when (campo.trim().lowercase()) {
//            "nombre" -> binding.textInputLayoutNombre.error = null
//            "id" -> binding.textInputLayoutID.error = null
//            "email" -> binding.textInputLayoutEmail.error = null
//            "all" -> {
//                binding.textInputLayoutID.error = null
//                binding.textInputLayoutNombre.error = null
//                binding.textInputLayoutEmail.error = null
//            }
//        }
//    }

    // Con varios campos a la vez pero sin companion object.
//    private fun limpiarErrores(vararg campos: String) {
//        // Si no se pasan parámetros, limpia todos
//        if (campos.isEmpty()) {
//            binding.textInputLayoutID.error = null
//            binding.textInputLayoutNombre.error = null
//            binding.textInputLayoutEmail.error = null
//            return
//        }
//
//        // Limpia solo los campos especificados
//        campos.forEach { campo ->
//            when (campo) {
//                "nombre" -> binding.textInputLayoutNombre.error = null
//                "id" -> binding.textInputLayoutID.error = null
//                "email" -> binding.textInputLayoutEmail.error = null
//            }
//        }
//    }

    // Prueba funcion para eliminar errores con constantes y companion object
    private fun limpiarErrores(vararg campos: String) {
        // Si no hay parámetros → no hacer nada
        if (campos.isEmpty()) {
            return
        }

        // Si el primer (y único) parámetro es TODOS → limpiar TODO
        if (campos.size == 1 && campos[0] == TODOS) {
            binding.textInputLayoutID.error = null
            binding.textInputLayoutNombre.error = null
            binding.textInputLayoutEmail.error = null
            return
        }

        // Si hay parámetros específicos → limpiar solo esos
        campos.forEach { campo ->
            when (campo.trim().lowercase()) {
                NOMBRE -> binding.textInputLayoutNombre.error = null
                ID -> binding.textInputLayoutID.error = null
                EMAIL -> binding.textInputLayoutEmail.error = null
            }
        }
    }
    companion object {
        const val NOMBRE = "nombre"
        const val ID = "id"
        const val EMAIL = "email"
        const val TODOS = "all"
    }

} // Fin de la clase









