package com.moly.edi.data.dataSource.local.entity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "categorias",
    foreignKeys = [
        ForeignKey(
            entity = NoticiaEntity::class,
            parentColumns = ["id"],
            childColumns = ["noticiaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["noticiaId"])]
)
data class CategoriaEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val noticiaId: String,
    val nombre: String
)