package com.moly.edi.data.repository

import com.moly.edi.data.model.NoticiaUnsa

interface NoticiasRepository {
    fun obtenerNoticiasUnsa(): List<NoticiaUnsa>
    fun obtenerNoticiasPorCategoria(categoria: String): List<NoticiaUnsa>
}

