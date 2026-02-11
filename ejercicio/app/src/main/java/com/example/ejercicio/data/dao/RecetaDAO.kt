package com.example.ejercicio.data.dao

import com.example.ejercicio.data.model.Receta

interface RecetaDAO {

    fun insertarReceta (receta : Receta): Long

    fun leerRecetas() : List<Receta>

    fun leerRecetaPorId(id : Int) : Receta

    fun borrarReceta(id : Int) : Int

    fun existeUsuario(id: Int?): Boolean
}