package com.moly.edi.data.dataSource.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class NoticiaConCategorias(
    @Embedded val noticia: NoticiaEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "noticiaId"
    )
    val categorias: List<CategoriaEntity>
)