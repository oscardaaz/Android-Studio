package com.example.actividadsqlite.data.dao

import com.example.actividadsqlite.data.model.Usuario

interface UsuarioDAO {

    fun insertarUsuario(usuario : Usuario) : Long
    fun leerUsuarios() : List<Usuario>
    fun leerUsuarioPorId(id: Int) : Usuario
    fun actualizarUsuario(usuario: Usuario) : Int
    fun borrarUsuario(id : Int) : Int
    fun borrarTodosLosUsuarios(): Int
}