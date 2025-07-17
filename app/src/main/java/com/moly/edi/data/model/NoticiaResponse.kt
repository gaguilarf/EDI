package com.moly.edi.data.model

data class NoticiaResponse(
    val autor: String,
    val cant_reacciones: Int,
    val categorias: List<String>,
    val descripcion: String,
    val fecha_publicacion: String,
    val id: String,
    val tipo: String,
    val titulo: String
)
