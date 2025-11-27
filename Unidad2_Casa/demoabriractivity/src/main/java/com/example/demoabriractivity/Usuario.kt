package com.example.demoabriractivity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario(
    val username: String,
    val telefono: String,
    val email: String
) : Parcelable {

}
