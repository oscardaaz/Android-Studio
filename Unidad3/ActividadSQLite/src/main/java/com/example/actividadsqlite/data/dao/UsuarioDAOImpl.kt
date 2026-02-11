package com.example.actividadsqlite.data.dao

import android.content.ContentValues
import com.example.actividadsqlite.data.database.UsuariosSQLiteHelper
import com.example.actividadsqlite.data.model.Usuario

class UsuarioDAOImpl(
    private val dbHelper: UsuariosSQLiteHelper
    ) : UsuarioDAO {

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

    override fun leerUsuariosOrdenadosPorNombre(): List<Usuario> {
        val listaUsuarios = mutableListOf<Usuario>()
        val db= dbHelper.readableDatabase

        val query = "SELECT * FROM ${UsuariosSQLiteHelper.TABLE_NAME} ORDER BY ${
            UsuariosSQLiteHelper.COLUMN_ID}"
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

    override fun leerUsuarioPorEmail(email: String): Usuario {
        val listaUsuarios = mutableListOf<Usuario>()
        val db= dbHelper.readableDatabase
        val query = "SELECT * FROM ${UsuariosSQLiteHelper.TABLE_NAME} AS a WHERE a.email = ?"
        // En SQL seria --> SELECT * FROM usuarios
        val cursor = db.rawQuery(query, arrayOf(email))

        if (cursor.moveToNext()){
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val email = cursor.getString(2)
                listaUsuarios.add(Usuario(id,nombre,email))
            }while (cursor.moveToNext())
        }

        val usuario: Usuario = listaUsuarios.first()
        cursor.close()
        db.close()

        return usuario
    }

    override fun buscarUsuarios(texto: String): List<Usuario> {
        TODO("Not yet implemented")
    }

    override fun actualizarNombre(id: Int, nuevoNombre: String): Int {

        if (existeUsuarioPorId(id)) return -1
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(UsuariosSQLiteHelper.COLUMN_NOMBRE, nuevoNombre)
        }

        val filasActualizadas = db.update (
            UsuariosSQLiteHelper.TABLE_NAME,
            values,
            "${UsuariosSQLiteHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        //UPDATE usuarios SET nombre = ?, email = ? WHERE id = ?
        db.close()
        return filasActualizadas
    }

    override fun actualizarUsuario(usuario: Usuario): Int {

        if (!existeUsuario(usuario.email)) return 0

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(UsuariosSQLiteHelper.COLUMN_NOMBRE, usuario.nombre)
            put(UsuariosSQLiteHelper.COLUMN_EMAIL, usuario.email)
        }

        val filasActualizadas = db.update (
            UsuariosSQLiteHelper.TABLE_NAME,
            values,
            "${UsuariosSQLiteHelper.COLUMN_ID} = ?",
            arrayOf(usuario.id.toString())
        )
        //UPDATE usuarios SET nombre = ?, email = ? WHERE id = ?
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

        db.update (
            UsuariosSQLiteHelper.TABLE_NAME,
            values,
            "${UsuariosSQLiteHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        //UPDATE usuarios SET nombre = ?, email = ? WHERE id = ?
        db.close()

        return true
    }

    // Se puede poner private fun, estaba asi, pero la hago publica
    fun existeUsuario(email: String): Boolean {
        val db = dbHelper.readableDatabase

        val query = """
            SELECT ${UsuariosSQLiteHelper.COLUMN_EMAIL} 
            FROM ${UsuariosSQLiteHelper.TABLE_NAME}
            WHERE ${UsuariosSQLiteHelper.COLUMN_EMAIL} = ?
        """.trimIndent()

        val cursor = db.rawQuery(query,arrayOf(email))
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

        val cursor = db.rawQuery(query,arrayOf(id.toString()))
        val existe = cursor.count > 0

        cursor.close()
        db.close()
        return existe
    }

    override fun borrarUsuario(email: String): Int {

        if (!existeUsuario(email)) return 0

        val db = dbHelper.writableDatabase

        val filasBorradas = db.delete (
            UsuariosSQLiteHelper.TABLE_NAME,
            "${UsuariosSQLiteHelper.COLUMN_EMAIL} = ?",
            arrayOf(email)
        )

        //DELETE * FROM usuarios WHERE id = ?
        db.close()
        return filasBorradas
    }

    override fun borrarUsuariosPorDominio(dominio: String): Int {

        val db = dbHelper.writableDatabase

        // Opción 3: Sin placeholders (no recomendado si el valor viene de usuario)
        val filasBorradas = db.delete (
            UsuariosSQLiteHelper.TABLE_NAME,
            "${UsuariosSQLiteHelper.COLUMN_EMAIL} LIKE '%@$dominio%'",
            null
        )
        // Opción 1: Usando placeholders con concatenación en el array
//        val filasBorradas = db.delete(
//            UsuariosSQLiteHelper.TABLE_NAME,
//            "${UsuariosSQLiteHelper.COLUMN_EMAIL} LIKE ?",
//            arrayOf("%$dominio%")
//        )

        // Opción 2: Usando placeholders y concatenando en el where
//        val filasBorradas = db.delete(
//            UsuariosSQLiteHelper.TABLE_NAME,
//            "${UsuariosSQLiteHelper.COLUMN_EMAIL} LIKE '%' || ? || '%'",
//            arrayOf(dominio)
//        )

        //DELETE * FROM usuarios WHERE id = ?
        db.close()
        return filasBorradas
    }

    override fun borrarTodosLosUsuarios(): Int {
        val db = dbHelper.writableDatabase

        val filasBorradas = db.delete (
            UsuariosSQLiteHelper.TABLE_NAME,
            null,
            null
        )

        //DELETE FROM usuarios
        db.close()
        return filasBorradas
    }

    override fun contarUsuarios(): Int {

        val db= dbHelper.readableDatabase

        val query = "SELECT COUNT(*) FROM ${UsuariosSQLiteHelper.TABLE_NAME}"
        // En SQL seria --> SELECT * FROM usuarios

        var usuarios = 0
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToNext()){
            do {
                usuarios = cursor.getInt(0)
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return usuarios

    }


}