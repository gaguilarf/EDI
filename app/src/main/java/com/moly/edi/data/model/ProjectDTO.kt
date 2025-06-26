package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName

data class ProjectDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("id_usuario")
    val id_usuario: String,
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("descripcion")
    val descripcion: String
)
