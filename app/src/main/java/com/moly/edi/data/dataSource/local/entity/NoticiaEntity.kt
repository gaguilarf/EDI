package com.moly.edi.data.dataSource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noticias")
data class NoticiaEntity(
    @PrimaryKey val id: String,
    val titulo: String,
    val contenido: String,
    val fecha: String?,
    val esImportante: Boolean
)
