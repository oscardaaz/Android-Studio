package com.example.actividadsqlite.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsuariosSQLiteHelper (contexto: Context) : SQLiteOpenHelper (

    contexto,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object{
        const val DATABASE_NAME = "usuarios.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "usuarios"

        //Columnas
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_EMAIL = "email"
    }

    // Sentencia SQL para crear la tabla de usuarios
    private val sqlCreateTable = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NOMBRE TEXT,
            $COLUMN_EMAIL TEXT
        )
    """
    // Equivalente sin constantes:
    // Create table usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT,
    // Nombre TEXT, email TEXT)

    // Sentencia SQL para eliminar la tabla
    private val sqlDropTable = "DROP TABLE IF EXISTS $TABLE_NAME"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL(sqlDropTable)
        onCreate(db)
    }

} // Fin de la clase UsuariosSQLiteHelper
