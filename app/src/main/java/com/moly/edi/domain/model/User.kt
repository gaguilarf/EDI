package com.moly.edi.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val nombre: String,
    val correo: String,
    val telefono: String? = null,
    val linkedin: String? = null,
    val github: String? = null,
    val instagram: String? = null,
    val tecnologias: List<String> = emptyList(),
    val proyectos: List<Project> = emptyList()
)