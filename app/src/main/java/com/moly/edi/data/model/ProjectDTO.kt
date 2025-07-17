package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName
import com.moly.edi.domain.model.Project

data class ProjectDTO(
    @SerializedName("id_documento")
    val id: String?,
    @SerializedName("titulo")
    val titulo: String?,
    @SerializedName("descripcion")
    val descripcion: String?
)

fun ProjectDTO.toDomain(): Project {
    return Project(
        id = this.id ?: "0",
        userId = "", // No se usa en Firestore
        titulo = this.titulo ?: "",
        descripcion = this.descripcion ?: ""
    )
}
