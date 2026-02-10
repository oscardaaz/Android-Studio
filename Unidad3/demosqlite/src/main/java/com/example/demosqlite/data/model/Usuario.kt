package com.example.demosqlite.data.model

data class Usuario (
    val id: Int? = null,
    val nombre: String,
    val email: String
) {
    override fun toString(): String {
        return "id = $id | nombre = '$nombre' | email = '$email'"
    }
}


