package com.example.actividadsqlite.data.dao

import com.example.actividadsqlite.data.model.Usuario

interface UsuarioDAOActividad {

    fun insertarUsuarioSiEmailNoExiste(usuario: Usuario): Long

    fun leerUsuarioPorEmail(email: String): Usuario?

    fun leerUsuariosOrdenadosPorNombre(): List<Usuario>

    fun buscarUsuarios(texto: String): List<Usuario>

    fun actualizarNombre(id: Int, nuevoNombre: String): Int

    fun actualizarEmailSiDisponible(id: Int, nuevoEmail: String): Boolean

    fun borrarUsuariosPorDominio(dominio: String): Int

    fun contarUsuarios(): Int
}
