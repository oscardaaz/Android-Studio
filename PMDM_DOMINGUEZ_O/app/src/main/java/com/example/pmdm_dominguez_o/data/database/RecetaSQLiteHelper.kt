package com.example.pmdm_dominguez_o.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecetaSQLiteHelper (contexto: Context) : SQLiteOpenHelper(
    contexto,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
){
    companion object{
        const val DATABASE_NAME = "dbrecetas"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "recetas"

        //Columnas
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_PREPARATION_TIME = "preparation_time"
    }

    // Sentencia SQL para crear la tabla de usuarios
    private val sqlCreateTable = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_CATEGORY TEXT NOT NULL,
            $COLUMN_PREPARATION_TIME INTEGER NOT NULL
        )
    """
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

}