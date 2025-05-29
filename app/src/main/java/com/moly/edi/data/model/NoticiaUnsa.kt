package com.moly.edi.data.model

data class NoticiaUnsa(
    val id: Int,
    val titulo: String,
    val contenido: String,
    val fecha: String,
    val categoria: String,
    val esImportante: Boolean = false
)

