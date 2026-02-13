package com.example.pmdm_dominguez_o.data.dao

import com.example.pmdm_dominguez_o.data.model.Receta

interface RecetaDAO {

    fun borrarPorId(id: Int) : Int
    fun insertarReceta(receta : Receta): Long
    fun consultarRecetas() : List<Receta>

}