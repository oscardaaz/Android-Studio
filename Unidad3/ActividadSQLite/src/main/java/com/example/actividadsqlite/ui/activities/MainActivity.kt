package com.example.actividadsqlite.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadsqlite.R
import com.example.actividadsqlite.databinding.ActivityMainBinding
import com.example.actividadsqlite.data.dao.UsuarioDAOImpl
import com.example.actividadsqlite.data.database.UsuariosSQLiteHelper
import com.example.actividadsqlite.data.model.Usuario
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var operaciones: UsuarioDAOImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val toolbar = findViewById<MaterialToolbar>(R.id.cbMaterialToolbar)
        val toolbar = binding.cbMaterialToolbar

        toolbar.inflateMenu(R.menu.menu_appbar)


//        toolbar.setNavigationOnClickListener {
//            //finish()
//            onBackPressedDispatcher.onBackPressed()
//        }
        //Se puede poner aqui o en el XML
//        toolbar.title = "Lista de Usuarios"
//        toolbar.subtitle = "Programacion Moviles"

        //Inicializamos el DAO
        val dbHelper = UsuariosSQLiteHelper(this)
        operaciones = UsuarioDAOImpl(dbHelper)

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
                else -> false
            }

        }

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
        limpiarErrores()
//        val idEmail = binding.textInputLayoutEmail.editText?.text.toString().trim()

        val idEmail = binding.textInputLayoutID.editText?.text.toString().trim()
        val id = binding.textInputLayoutID.editText?.text.toString().trim()

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

            if (operaciones.existeUsuarioPorId(id.toInt())) {
                val usuario = operaciones.leerUsuarioPorId(idEmail.toInt())

                mostrarMensaje("Mostrando usuario con id: $id")
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("DATOS", usuario.toString())
                startActivity(intent)

            }else{
                mostrarMensaje("El usuario con id: $idEmail ,no existe")
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("DATOS", "El usuario con id: $id ,no existe")
                startActivity(intent)

            }

            limpiarFocus()
            limpiarCampos()
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
        limpiarErrores()
//        val dominio = binding.textInputLayoutEmail.editText?.text.toString().trim()
        val idTexto = binding.textInputLayoutID.editText?.text.toString().trim()

//        // Comprobar si esta vacio
        if (idTexto.isEmpty()) {
            mostrarMensaje("Introduce id para eliminar")
            binding.textInputLayoutID.error = "El campo es obligatorio para eliminar"
            return
        }

        // Si no esta vacio, comprobamos que sea un número valido.
        val id = idTexto.toIntOrNull()
        if (id == null) {
            mostrarMensaje("El id tiene que ser un nº valido")
            binding.textInputLayoutID.error = "El campo tiene que ser un n` valido"
            return
        }

        val filasEliminadas = operaciones.borrarUsuarioPorID(idTexto.toInt())
        if (filasEliminadas > 0) {
            mostrarMensaje("Eliminación correcta del usuario $id")
            limpiarCampos()
            actualizarListaUsuarios()
        } else {
            mostrarMensaje("El usuario con id: $id no existe")
        }

        limpiarErrores()
        limpiarCampos()

//        val filasEliminadas = operaciones.borrarUsuariosPorDominio(dominio)
//        mostrarMensaje("Eliminacion correcta")
//        actualizarListaUsuarios()
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
        limpiarErrores()

        val idTexto = binding.textInputLayoutID.editText?.text.toString().trim()
        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()
        val validarEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        var error = false
        // Comprobar si esta vacio
        if (idTexto.isEmpty() || nombre.isEmpty() || email.isEmpty() || idTexto.toIntOrNull() == null) {
            mostrarMensaje("Introduce los campos obligatorios")
            if (idTexto.isEmpty()) binding.textInputLayoutID.error = "El id es obligatorio para actualizar"
            if (nombre.isEmpty()) binding.textInputLayoutNombre.error = "El nombre es obligatorio para actualizar"
            if (email.isEmpty()) binding.textInputLayoutEmail.error = "El email es obligatorio para actualizar"
            if  (!idTexto.isEmpty() && idTexto.toIntOrNull() == null) {
                binding.textInputLayoutID.error = "El id tiene que ser un nº valido"
            }
            if (!email.isEmpty() && !validarEmail) {
                mostrarMensaje("El email no tiene el formato correcto")
                binding.textInputLayoutEmail.error = "Estructura no valida, prueba: 'email@dominio.com' "

            }
            return
        }

        // Si no esta vacio, comprobamos que sea un número valido.
//        val id = idTexto.toIntOrNull()
//        if (id == null) {
//            mostrarMensaje("ID tiene que ser un nº ")
//            binding.textInputLayoutID.error = "El id tiene que ser un nº valido"
//            error = true
//        }


//        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
//        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()

        // TODO: Validar campos nombre e email.
        val usuarioActualizado = Usuario(
            id = idTexto.toInt(),
            nombre = nombre,
            email = email
        )
        val filasActualizadas = operaciones.actualizarUsuario(usuarioActualizado)
        if (filasActualizadas > 0) {
            mostrarMensaje("Actualizacion del usuario con id: $idTexto correcta")

        } else {
            mostrarMensaje("El usuario con id: $idTexto no existe en la BBDD")
            Log.d("MainActivity","El usuario con id: $idTexto no existe")
        }


//         Actualizar nombre por id
//        operaciones.actualizarNombre(id,nombre)
//        actualizarListaUsuarios()
//
//        //Actualizar email si no existe otro email igual
//       val actualizarEmail = operaciones.actualizarEmailSiDisponible(19,"nuevoEemail.com")
//        if (actualizarEmail){
//            mostrarMensaje("El usuario ha sido actualizado correctamente")
//            actualizarListaUsuarios()
//        }else {
//            mostrarMensaje("No se ha podido actualizar ese usuario")
//        }

        actualizarListaUsuarios()
        limpiarErrores()
        limpiarCampos()
        limpiarFocus()
    }

    private fun manejarInsercion() {
        limpiarErrores()

        val nombre = binding.textInputLayoutNombre.editText?.text.toString().trim()
        val email = binding.textInputLayoutEmail.editText?.text.toString().trim()
        val validarEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

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
        if (!validarEmail) {
            mostrarMensaje("El email no tiene el formato correcto")
            binding.textInputLayoutEmail.error = "Estructura no valida, prueba: 'email@dominio.com' "
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
            actualizarListaUsuarios()
        } else {
            mostrarMensaje("Error al insertar usuario")
        }
        limpiarErrores()
        limpiarCampos()
        limpiarFocus()

    }

    private fun limpiarFocus(){
        binding.textInputLayoutID.clearFocus()
        binding.textInputLayoutNombre.clearFocus()
        binding.textInputLayoutEmail.clearFocus()
    }
    private fun limpiarCampos() {
        binding.textInputLayoutID.editText?.setText("")
        binding.textInputLayoutNombre.editText?.setText("")
        binding.textInputLayoutEmail.editText?.setText("")
    }

    private fun limpiarErrores(){
        binding.textInputLayoutID.error = null
        binding.textInputLayoutNombre.error = null
        binding.textInputLayoutEmail.error = null
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
                else -> false
            }

        }

    }
    // Log.e (level.error) o Log.d (level:debug)
    private fun insertarLogcat (mensaje:String){
        Log.e(
            "MainActivity",
            mensaje
        )
    }

    private fun mostrarToast(mensaje : String){
        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_LONG).show()
    }
    private fun mostrarMensaje(mensaje: String) {
        Snackbar.make(
            binding.root,
            mensaje,
            Snackbar.LENGTH_LONG
        ).show()
    }

} // Fin de la clase







