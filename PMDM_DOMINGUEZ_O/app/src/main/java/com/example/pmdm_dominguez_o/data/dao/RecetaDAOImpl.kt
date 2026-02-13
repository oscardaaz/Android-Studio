package com.example.pmdm_dominguez_o.data.dao


import android.content.ContentValues
import com.example.pmdm_dominguez_o.data.database.RecetaSQLiteHelper
import com.example.pmdm_dominguez_o.data.model.Receta
import kotlin.compareTo
import kotlin.text.insert

class RecetaDAOImpl(
    private val dbHelper: RecetaSQLiteHelper
) : RecetaDAO {


    // Funcion borrar por id
    override fun borrarPorId(id: Int): Int {
        if (!existeReceta(id)) return 0

        val db = dbHelper.writableDatabase

        val filasBorradas = db.delete (
            RecetaSQLiteHelper.TABLE_NAME,
            "${RecetaSQLiteHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )

        //DELETE * FROM usuarios WHERE id = ?
        db.close()
        return filasBorradas
    }

    // funcion insertar receta.
    override fun insertarReceta(receta: Receta): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            // put("nombre" , usuario.nombre)
            // put("email", usuario.email)
            put(RecetaSQLiteHelper.COLUMN_NAME , receta.name)
            put(RecetaSQLiteHelper.COLUMN_CATEGORY, receta.category)
            put(RecetaSQLiteHelper.COLUMN_PREPARATION_TIME, receta.preparation_time)
        }
        val resultado = db.insert(
            RecetaSQLiteHelper.TABLE_NAME,
            null,
            values
        )

        db.close()
        return resultado
    }

    // Funcion insertar receta
    override fun consultarRecetas() : List<Receta> {
        val listaUsuarios = mutableListOf<Receta>()
        val db= dbHelper.readableDatabase

        val query = "SELECT * FROM ${RecetaSQLiteHelper.TABLE_NAME}"
        // En SQL seria --> SELECT * FROM usuarios

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToNext()){
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val category = cursor.getString(2)
                val preparacion = cursor.getInt(3)
                listaUsuarios.add(Receta(id,name,category,preparacion))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaUsuarios
    }

    fun existeReceta(id: Int): Boolean {
        val db = dbHelper.readableDatabase

        val query = """
            SELECT ${RecetaSQLiteHelper.COLUMN_ID} 
            FROM ${RecetaSQLiteHelper.TABLE_NAME}
            WHERE ${RecetaSQLiteHelper.COLUMN_ID} = ?
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(id.toString()))
        val existe = cursor.count > 0

        cursor.close()
        db.close()
        return existe
    }

}