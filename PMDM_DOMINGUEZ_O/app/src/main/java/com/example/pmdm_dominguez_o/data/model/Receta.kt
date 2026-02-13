package com.example.pmdm_dominguez_o.data.model

data class Receta (
    val id: Int? = null,
    val name: String,
    val category: String,
    val preparation_time: Int?
) {
    override fun toString(): String {
        return "Id=$id, Name=$name, Category=$category, Prep time=$preparation_time min"
    }
}