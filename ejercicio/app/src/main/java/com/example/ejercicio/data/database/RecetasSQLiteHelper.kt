package com.example.ejercicio.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecetasSQLiteHelper (contexto: Context) : SQLiteOpenHelper (
    contexto,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        const val DATABASE_NAME = "dbRecetas"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "recetas"

        //COLUMNAS

        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_CATEGORIA = "categoria"
        const val COLUMN_TIEMPO_PREPARACION = "tiempo_preparacion"
    }

    private val sqlCreateTable = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NOMBRE TEXT NOT NULL,
            $COLUMN_CATEGORIA TEXT NOT NULL,
            $COLUMN_TIEMPO_PREPARACION INTEGER NOT NULL
        )
    """

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
}