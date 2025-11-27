package com.example.demoabriractivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class DemoAbrirActivity : AppCompatActivity() {

    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilTelefono: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var btnAceptar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_abrir)

        // Inicializamos los controles
        btnAceptar = findViewById(R.id.btnAceptar)
        tilUsername = findViewById(R.id.tilUsername)
        tilTelefono = findViewById(R.id.tilTelephone)
        tilEmail = findViewById(R.id.tilEmail)

        // Asignamos accion al boton Aceptar
        configurarBotonAceptar()

    }

    @SuppressLint("SuspiciousIndentation")
    private fun configurarBotonAceptar() {
        btnAceptar.setOnClickListener {
            val exito = validarCampos(tilUsername, tilEmail)
            if (exito) {
                Toast.makeText(this, "Formulario válido", Toast.LENGTH_LONG).show()
//                val intent = Intent(this, SegundaActivity::class.java)
//                intent.putExtra("USERNAME", tilUsername.editText?.text?.toString()?.trim() ?: "")
//                intent.putExtra("TELEFONO", tilTelefono.editText?.text?.toString()?.trim() ?: "")
//                intent.putExtra("EMAIL", tilEmail.editText?.text?.toString()?.trim() ?: "")
//                startActivity(intent)

//                //Opcion con apply
//                val intent = Intent(this, SegundaActivity::class.java).apply {
//                    putExtra(
//                        "USERNAME",
//                        tilUsername.editText?.text?.toString()?.trim() ?: ""
//                    )
//                    putExtra(
//                        "TELEFONO",
//                        tilTelefono.editText?.text?.toString()?.trim() ?: ""
//                    )
//                    putExtra(
//                        "EMAIL",
//                        tilEmail.editText?.text?.toString()?.trim() ?: ""
//                    )
//                }

                val usuario = Usuario (
                    username = tilUsername.editText?.text?.toString()?.trim() ?: "",
                    telefono =  tilTelefono.editText?.text?.toString()?.trim() ?: "",
                    email = tilEmail.editText?.text?.toString()?.trim() ?: ""
                )
                val intent = Intent(this, SegundaActivity::class.java)
                    intent.putExtra("OBJETO_USUARIO", usuario)
                    startActivity(intent)

//                startActivity(intent)

            } else {
                Toast.makeText(this, "Hay errores en el formulario", Toast.LENGTH_LONG).show()
            }

        }
    }// Fin de configurarBotonAceptar

    private fun validarCampos(
        tilUsername: TextInputLayout,
        tilEmail: TextInputLayout
    ): Boolean {

        val usernameValido = validarUsuario(tilUsername)
        val emailValido = validarEmail(tilEmail)

        return usernameValido && emailValido
    }

    private fun validarEmail(tilEmail: TextInputLayout): Boolean {
        val email =  tilEmail.editText?.text?.toString()?.trim() ?: ""
        return when {
            email.isEmpty() -> {
                tilEmail.error = "El email es obligatorio"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {

                tilEmail.error = "No es un email válido"
                false
            }
            else -> {
                tilEmail.error = null
                true
            }
        }
    }

    private fun validarUsuario(tilUsername: TextInputLayout): Boolean {
        val username = tilUsername.editText?.text?.toString()?.trim() ?: ""
        return when {
            username.isEmpty() -> {
                tilUsername.error = "El nombre de usuario es obligatorio"
                false
            }
            username.length > 10 -> {   // Redundante, porque ya lo manejamos en el hijo el max 10
                                        //  asi es para ver como se haria por codigo;
                tilUsername.error = "La longitud debe ser < 10"
                false
            }
            else -> {
                tilUsername.error = null
                true
            }
        }
    }

    fun manejarClickCancelar(view: View) {
        Toast.makeText(this, "Has pulsado cancelar", Toast.LENGTH_LONG).show()
    }
}