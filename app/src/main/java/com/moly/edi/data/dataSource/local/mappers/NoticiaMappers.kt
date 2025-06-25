package com.moly.edi.data.dataSource.local.mappers

import com.moly.edi.data.dataSource.api.entity.NoticiaResponse
import com.moly.edi.data.dataSource.local.entity.CategoriaEntity
import com.moly.edi.data.dataSource.local.entity.NoticiaConCategorias
import com.moly.edi.data.dataSource.local.entity.NoticiaEntity
import com.moly.edi.data.model.NoticiaUnsa

fun NoticiaResponse.toNoticiaEntity(): NoticiaEntity {
    return NoticiaEntity(
        id = this.id,
        titulo = this.titulo,
        contenido = this.descripcion,
        fecha = this.fecha_publicacion,
        esImportante = true
    )
}

fun NoticiaResponse.toCategoriaEntities(): List<CategoriaEntity> {
    return (this.categorias ?: emptyList()).map { nombre ->
        CategoriaEntity(
            noticiaId = this.id,
            nombre = nombre
        )
    }
}

fun NoticiaConCategorias.toDomain(): NoticiaUnsa {
    return NoticiaUnsa(
        id = noticia.id,
        titulo = noticia.titulo,
        contenido = noticia.contenido,
        fecha = noticia.fecha ?: "Fecha desconocida",
        categoria = categorias.map { it.nombre },
        esImportante = noticia.esImportante
    )
}