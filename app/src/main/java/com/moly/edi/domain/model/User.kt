package com.moly.edi.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "UserEntity")
data class User(

    val id: String? = "1",
    val nombre: String,
    val correo: String,
    val celular: String? = null,
    val linkedin: String? = null,
    val instagram: String? = null,
    val competencias: String? = null,
    val proyectos: List<Project> = emptyList()
)