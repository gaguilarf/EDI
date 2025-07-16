package com.moly.edi.data.model

data class User(
    val id: Int,
    val nombre: String,
    val correo: String,
    val telefono: String? = null,
    val linkedin: String? = null,
    val github: String? = null,
    val instagram: String? = null,
    val tecnologias: List<String> = emptyList(),
    val proyectos: List<Project> = emptyList()
)

data class Project(
    val id: Int,
    val titulo: String,
    val descripcion: String
)
