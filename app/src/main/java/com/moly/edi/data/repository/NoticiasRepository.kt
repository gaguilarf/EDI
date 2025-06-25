package com.moly.edi.data.repository

import com.moly.edi.data.model.NoticiaUnsa

interface NoticiasRepository {
    suspend fun obtenerNoticiasUnsa(): List<NoticiaUnsa>
    suspend fun obtenerNoticiasPorCategoria(categoria: String): List<NoticiaUnsa>
    suspend fun obtenerNoticiasLocal(): List<NoticiaUnsa>
    suspend fun sincronizarNoticias()
}

