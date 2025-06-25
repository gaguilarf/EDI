package com.moly.edi.data.repositoryImpl

import android.util.Log
import com.moly.edi.data.dataSource.api.entity.NoticiasService
import com.moly.edi.data.dataSource.local.dao.NoticiaDao
import com.moly.edi.data.dataSource.local.mappers.toCategoriaEntities
import com.moly.edi.data.dataSource.local.mappers.toDomain
import com.moly.edi.data.dataSource.local.mappers.toNoticiaEntity
import com.moly.edi.data.model.NoticiaUnsa
import com.moly.edi.data.repository.NoticiasRepository
import javax.inject.Inject

class NoticiasRepositoryImpl @Inject constructor(
    private val api: NoticiasService,
    private val dao: NoticiaDao
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

    override suspend fun obtenerNoticiasLocal(): List<NoticiaUnsa> {
        return dao.obtenerTodas().map { item ->
            item.toDomain()
        }
    }

    override suspend fun sincronizarNoticias() {
        try {
            val remotas = try {
                api.getNoticias()
            } catch (apiError: Exception) {
                Log.e("SYNC", "Error al obtener noticias de la API: ${apiError.message}")
                emptyList()
            }

            Log.d("SYNC", "Noticias obtenidas: ${remotas.size}")
            remotas.forEachIndexed { i, noticia ->
                Log.d("SYNC", "[$i] ${noticia.titulo}")
            }

            val noticias = remotas.map { item ->
                try {
                    item.toNoticiaEntity()
                } catch (e: Exception) {
                    Log.e("SYNC", "Error al mapear NoticiaEntity ${item.id}: ${e.message}")
                    throw e
                }
            }

            val categorias = remotas.flatMap { item ->
                try {
                    item.toCategoriaEntities()
                } catch (e: Exception) {
                    Log.e("SYNC", "Error al mapear CategoriaEntity para noticia ${item.id}: ${e.message}")
                    throw e
                }
            }

            Log.d("SYNC", "Noticias convertidas: ${noticias.size}, Categorías: ${categorias.size}")

            try {
                dao.borrarCategorias()
                dao.borrarNoticias()
                Log.d("SYNC", "Base de datos local limpiada")

                noticias.forEach { noticia ->
                    try {
                        dao.insertarNoticia(noticia)
                    } catch (e: Exception) {
                        Log.e("SYNC", "Error al insertar noticia ${noticia.id}: ${e.message}")
                    }
                }

                try {
                    dao.insertarCategorias(categorias)
                } catch (e: Exception) {
                    Log.e("SYNC", "Error al insertar categorías: ${e.message}")
                }

                Log.d("SYNC", "Noticias y categorías insertadas correctamente")

            } catch (e: Exception) {
                Log.e("SYNC", "Error durante limpieza o inserción en la base de datos: ${e.message}")
            }
        } catch (e: Exception) {
            Log.e("SYNC", "Error general en sincronización: ${e.message}")
        }
    }
}

