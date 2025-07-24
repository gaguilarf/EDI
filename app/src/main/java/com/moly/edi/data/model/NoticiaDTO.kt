package com.moly.edi.data.model

data class NoticiaDTO(
    val autor: String,
    val reacciones: Int,
    val categoria: String,
    val descripcion_larga: String,
    val descripcion_card: String,
    val fecha: String,
    val id: String,
    val titulo: String
)
