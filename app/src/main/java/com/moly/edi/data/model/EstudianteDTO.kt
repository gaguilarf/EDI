package com.moly.edi.data.model

data class EstudianteDTO(
    val aptitudes: List<String>,
    val carrera: String,
    val categoriasInteres: String,
    val palabrasClave: String,
    val semestre: Int,
    val sobreMi: String
)