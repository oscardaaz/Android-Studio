package com.example.ejercicio.data.model

data class Receta(
    val id: Int? = null,
    val nombre: String,
    val categoria: String,
    val tiempo_preparacion: Int
) {
    override fun toString(): String {
        return "Receta(id=$id, nombre='$nombre', categoria='$categoria', tiempo_preparacion=$tiempo_preparacion minutos)"
    }
}
