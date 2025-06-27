package com.moly.edi.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ProjectEntity",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class Project(
    @PrimaryKey()
    val id: String = "0",
    val userId: String,
    val titulo: String,
    val descripcion: String,
    val isSynced: Boolean = false,
    val deletedLocally: Boolean = false,
    val modifiedLocally: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastModified: Long = System.currentTimeMillis(),
)