package com.moly.edi.data.repository

import android.util.Log
import com.moly.edi.data.dataSource.remote.api.NoticiasService
import com.moly.edi.data.mapper.toDomain
import com.moly.edi.domain.model.Noticia
import com.moly.edi.domain.repository.NoticiasRepository
import javax.inject.Inject

class NoticiasRepositoryImpl @Inject constructor(
    private val api: NoticiasService
) : NoticiasRepository {

    private var noticiasUnsa: List<Noticia> = emptyList()

    override suspend fun obtenerNoticiasUnsa(): List<Noticia> {
        val respuestas = try {
            api.getNoticias()
        } catch (e: Exception) {
            emptyList()
        }

        noticiasUnsa = try {
            respuestas.map { response ->
                Log.d("MAP", "response autor: ${response.autor}")
                response.toDomain();
            }
        } catch (e: Exception) {
            Log.e("ERROR MAP", "Falló el mapeo: ${e.message}", e)
            emptyList()
        }
        Log.d("BBBBBBBBBBBB", "${noticiasUnsa}\ntamaño: ${noticiasUnsa.size}")
        return noticiasUnsa
    }

    override suspend fun obtenerNoticiasPorCategoria(categoria: String): List<Noticia> {
        return noticiasUnsa.filter { it.categoria.contains(categoria) }
    }
}

