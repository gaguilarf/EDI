package com.moly.edi.domain.repository

import com.moly.edi.domain.model.Noticia

interface NoticiasRepository {
    suspend fun obtenerNoticiasUnsa(): List<Noticia>
    suspend fun obtenerNoticiasPorCategoria(categoria: String): List<Noticia>
}

