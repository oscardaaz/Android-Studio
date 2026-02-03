package com.example.demosqlite.data.dao

import com.example.demosqlite.data.model.Usuario

interface UsuarioDAO {

    fun insertarUsuarioSiEmailNoExiste(usuario : Usuario) : Long
    fun leerUsuariosOrdenadosPorNombre() : List<Usuario>
    fun leerUsuarioPorEmail(email: String) : Usuario
    fun buscarUsuarios(texto: String): List<Usuario>
    fun actualizarNombre(id: Int, nuevoNombre: String): Int
    fun actualizarUsuario(usuario: Usuario) : Int
    fun borrarUsuario(email : String) : Int
    fun borrarUsuariosPorDominio(dominio: String): Int


}