package com.moly.edi.domain.repository

import com.moly.edi.data.model.NoticiaUnsa

interface NoticiasRepository {
    suspend fun obtenerNoticiasUnsa(): List<NoticiaUnsa>
    suspend fun obtenerNoticiasPorCategoria(categoria: String): List<NoticiaUnsa>
}

