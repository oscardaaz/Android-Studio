package com.example.demosqlite.data.dao

import android.content.ContentValues
import com.example.demosqlite.data.database.UsuariosSQLiteHelper
import com.example.demosqlite.data.model.Usuario

class UsuarioDAOImpl(
    private val dbHelper: UsuariosSQLiteHelper
    ) : UsuarioDAO {

    override fun insertarUsuario(usuario: Usuario): Long {

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            // put("nombre" , usuario.nombre)
            // put("email", usuario.email)
            put(UsuariosSQLiteHelper.COLUMN_NOMBRE , usuario.nombre)
            put(UsuariosSQLiteHelper.COLUMN_EMAIL, usuario.email)
        }
        val resultado = db.insert(
            UsuariosSQLiteHelper.TABLE_NAME,
            null,
            values
        )

        db.close()
        return resultado
    }

    override fun leerUsuarios(): List<Usuario> {
        val listaUsuarios = mutableListOf<Usuario>()
        val db= dbHelper.readableDatabase

        val query = "SELECT * FROM ${UsuariosSQLiteHelper.TABLE_NAME}"
        // En SQL seria --> SELECT * FROM usuarios

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToNext()){
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val email = cursor.getString(2)
                listaUsuarios.add(Usuario(id,nombre,email))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaUsuarios
    }

    override fun leerUsuarioPorId(id: Int): Usuario? {
        TODO("Not yet implemented")
    }

    override fun actualizarUsuario(usuario: Usuario): Int {
        TODO("Not yet implemented")
    }

    override fun borrarUsuario(id: Int): Int {
        TODO("Not yet implemented")
    }

    override fun borrarTodosLosUsuarios(): Int {
        TODO("Not yet implemented")
    }
}