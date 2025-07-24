package com.moly.edi.domain.model

data class Conecta(
    val id: String, // es el correo
    val carrera: String,
    val competencias: String? = null,
    val nombres: String? = null,
    val semestre: Int,
    val sobre_mi: String
)