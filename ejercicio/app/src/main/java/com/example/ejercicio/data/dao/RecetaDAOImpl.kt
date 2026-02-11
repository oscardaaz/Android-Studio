package com.example.ejercicio.data.dao

import android.content.ContentValues
import com.example.ejercicio.data.database.RecetasSQLiteHelper
import com.example.ejercicio.data.model.Receta
import kotlin.compareTo
import kotlin.text.insert

class RecetaDAOImpl(
    private val dbHelper: RecetasSQLiteHelper
) : RecetaDAO{
    override fun insertarReceta(receta: Receta): Long {

        if (existeUsuario(receta.id)) return 0

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(RecetasSQLiteHelper.COLUMN_NOMBRE, receta.nombre)
            put(RecetasSQLiteHelper.COLUMN_CATEGORIA, receta.categoria)
            put(RecetasSQLiteHelper.COLUMN_TIEMPO_PREPARACION, receta.tiempo_preparacion)
        }
        val resultado = db.insert(
            RecetasSQLiteHelper.TABLE_NAME,
            null,
            values

        )
        db.close()

        return resultado

    }

    override fun leerRecetas(): List<Receta> {
        val listaRecetas = mutableListOf<Receta>()
        val db= dbHelper.readableDatabase

        val query = "SELECT * FROM ${RecetasSQLiteHelper.TABLE_NAME} ORDER BY ${
            RecetasSQLiteHelper.COLUMN_ID}"
        // En SQL seria --> SELECT * FROM usuarios

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToNext()){
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val categoria = cursor.getString(2)
                val tiempo_preparacion = cursor.getInt(3)
                listaRecetas.add(Receta(id,nombre,categoria,tiempo_preparacion))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaRecetas
    }

    override fun leerRecetaPorId(id: Int): Receta {
        TODO("Not yet implemented")
    }

    override fun borrarReceta(id: Int): Int {
        TODO("Not yet implemented")
    }




    override fun existeUsuario(id: Int?): Boolean {
        val db = dbHelper.readableDatabase

        val query = """
            SELECT ${RecetasSQLiteHelper.COLUMN_ID} 
            FROM ${RecetasSQLiteHelper.TABLE_NAME}
            WHERE ${RecetasSQLiteHelper.COLUMN_ID} = ?
        """.trimIndent()

        val cursor = db.rawQuery(query,arrayOf(id.toString()))
        val existe = cursor.count > 0

        cursor.close()
        db.close()
        return existe
    }
}