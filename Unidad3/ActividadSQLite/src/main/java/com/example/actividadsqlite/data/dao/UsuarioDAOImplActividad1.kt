package com.example.actividadsqlite.data.dao

import android.content.ContentValues
import com.example.actividadsqlite.data.database.UsuariosSQLiteHelper
import com.example.actividadsqlite.data.model.Usuario

class UsuarioDAOImplActividad1(
    private val dbHelper: UsuariosSQLiteHelper
    ) : UsuarioDAOActividad {

    override fun insertarUsuarioSiEmailNoExiste(usuario: Usuario): Long {

        if (existeUsuario(usuario.email)) return -1
        else {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(UsuariosSQLiteHelper.COLUMN_NOMBRE, usuario.nombre)
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
    }

    override fun leerUsuarioPorEmail(email: String): Usuario? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${UsuariosSQLiteHelper.TABLE_NAME} AS a WHERE a.email = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        var usuario: Usuario? = null

        if (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val emailUsuario = cursor.getString(2)
            usuario = Usuario(id, nombre, emailUsuario)
        }

        cursor.close()
        db.close()

        return usuario
    }

    override fun leerUsuariosOrdenadosPorNombre(): List<Usuario> {
        val listaUsuarios = mutableListOf<Usuario>()
        val db = dbHelper.readableDatabase

        val query = "SELECT * FROM ${UsuariosSQLiteHelper.TABLE_NAME} ORDER BY ${
            UsuariosSQLiteHelper.COLUMN_NOMBRE} ASC"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToNext()) {
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val email = cursor.getString(2)
                listaUsuarios.add(Usuario(id, nombre, email))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaUsuarios
    }

    override fun buscarUsuarios(texto: String): List<Usuario> {
        val listaUsuarios = mutableListOf<Usuario>()
        val db = dbHelper.readableDatabase

        val query = """
        SELECT * FROM ${UsuariosSQLiteHelper.TABLE_NAME}
        WHERE ${UsuariosSQLiteHelper.COLUMN_NOMBRE} LIKE ?
        OR ${UsuariosSQLiteHelper.COLUMN_EMAIL} LIKE ?
        ORDER BY ${UsuariosSQLiteHelper.COLUMN_NOMBRE} ASC
    """.trimIndent()

        val textoBusqueda = "%$texto%"
        val cursor = db.rawQuery(query, arrayOf(textoBusqueda, textoBusqueda))

        if (cursor.moveToNext()) {
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val email = cursor.getString(2)
                listaUsuarios.add(Usuario(id, nombre, email))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaUsuarios
    }

    override fun actualizarNombre(id: Int, nuevoNombre: String): Int {

        if (!existeUsuarioPorId(id)) return -1

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(UsuariosSQLiteHelper.COLUMN_NOMBRE, nuevoNombre)
        }

        val filasActualizadas = db.update(
            UsuariosSQLiteHelper.TABLE_NAME,
            values,
            "${UsuariosSQLiteHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )

        db.close()
        return filasActualizadas
    }

    override fun actualizarEmailSiDisponible(
        id: Int,
        nuevoEmail: String
    ): Boolean {
        if (!existeUsuarioPorId(id)) return false
        if (existeUsuario(nuevoEmail)) return false

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(UsuariosSQLiteHelper.COLUMN_EMAIL, nuevoEmail)
        }

        db.update(
            UsuariosSQLiteHelper.TABLE_NAME,
            values,
            "${UsuariosSQLiteHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )

        db.close()
        return true
    }

    override fun borrarUsuariosPorDominio(dominio: String): Int {

        val db = dbHelper.writableDatabase

        val filasBorradas = db.delete(
            UsuariosSQLiteHelper.TABLE_NAME,
            "${UsuariosSQLiteHelper.COLUMN_EMAIL} LIKE ?",
            arrayOf("%@$dominio")
        )

        db.close()
        return filasBorradas
    }

    override fun contarUsuarios(): Int {

        val db = dbHelper.readableDatabase
        val query = "SELECT COUNT(*) FROM ${UsuariosSQLiteHelper.TABLE_NAME}"

        var usuarios = 0
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            usuarios = cursor.getInt(0)
        }

        cursor.close()
        db.close()
        return usuarios
    }

    fun existeUsuario(email: String): Boolean {
        val db = dbHelper.readableDatabase

        val query = """
        SELECT ${UsuariosSQLiteHelper.COLUMN_EMAIL} 
        FROM ${UsuariosSQLiteHelper.TABLE_NAME}
        WHERE ${UsuariosSQLiteHelper.COLUMN_EMAIL} = ?
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(email))
        val existe = cursor.count > 0

        cursor.close()
        db.close()
        return existe
    }

    fun existeUsuarioPorId(id: Int): Boolean {
        val db = dbHelper.readableDatabase

        val query = """
        SELECT ${UsuariosSQLiteHelper.COLUMN_ID} 
        FROM ${UsuariosSQLiteHelper.TABLE_NAME}
        WHERE ${UsuariosSQLiteHelper.COLUMN_ID} = ?
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(id.toString()))
        val existe = cursor.count > 0

        cursor.close()
        db.close()
        return existe
    }
}