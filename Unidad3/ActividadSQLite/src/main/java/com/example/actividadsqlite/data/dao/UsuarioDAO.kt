package com.example.actividadsqlite.data.dao

import com.example.actividadsqlite.data.model.Usuario

interface UsuarioDAO {

    fun insertarUsuarioSiEmailNoExiste(usuario : Usuario) : Long
    fun insertarUsuario(usuario: Usuario): Long
    fun leerUsuariosOrdenadosPorNombre() : List<Usuario>
    fun leerUsuarioPorEmail(email: String) : Usuario
    fun buscarUsuarios(texto: String): List<Usuario>
    fun actualizarNombre(id: Int, nuevoNombre: String): Int
    fun actualizarUsuario(usuario: Usuario) : Int
    fun actualizarEmailSiDisponible(id: Int, nuevoEmail: String): Boolean
    fun borrarUsuario(email : String) : Int

    fun borrarUsuarioPorID(id : Int) : Int
    fun borrarUsuariosPorDominio(dominio: String): Int

    fun borrarTodosLosUsuarios(): Int

    fun contarUsuarios(): Int
}