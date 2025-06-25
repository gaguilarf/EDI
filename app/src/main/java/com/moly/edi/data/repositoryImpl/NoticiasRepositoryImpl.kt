package com.moly.edi.data.repositoryImpl

import android.util.Log
import com.moly.edi.data.dataSource.api.entity.NoticiasService
import com.moly.edi.data.model.NoticiaUnsa
import com.moly.edi.data.repository.NoticiasRepository
import javax.inject.Inject

class NoticiasRepositoryImpl @Inject constructor(
    private val api: NoticiasService
) : NoticiasRepository {

    private var noticiasUnsa: List<NoticiaUnsa> = emptyList()

    override suspend fun obtenerNoticiasUnsa(): List<NoticiaUnsa> {
        val respuestas = try {
            api.getNoticias()
        } catch (e: Exception) {
            emptyList()
        }

        noticiasUnsa = try {
            respuestas.map { response ->
                Log.d("MAP", "response autor: ${response.autor}")
                NoticiaUnsa(
                    id = response.id,
                    titulo = response.titulo,
                    contenido = response.descripcion,
                    fecha = response.fecha_publicacion?:"Fecha desconocida",
                    categoria = response.categorias ?:emptyList(),
                    esImportante = true
                )
            }
        } catch (e: Exception) {
            Log.e("ERROR MAP", "Falló el mapeo: ${e.message}", e)
            emptyList()
        }
        Log.d("BBBBBBBBBBBB", "${noticiasUnsa}\ntamaño: ${noticiasUnsa.size}")
        return noticiasUnsa
    }

    override suspend fun obtenerNoticiasPorCategoria(categoria: String): List<NoticiaUnsa> {
        return noticiasUnsa.filter { it.categoria.contains(categoria) }
    }
}

