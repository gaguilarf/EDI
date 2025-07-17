package com.moly.edi.data.model

data class NoticiaUnsa(
    val id: String,
    val titulo: String,
    val contenido: String,
    val fecha: String,
    val categoria: List<String>,
    val esImportante: Boolean = false
)

