package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName

data class RedesDTO(
    @SerializedName("github")
    val github: String? = null,
    @SerializedName("instagram")
    val instagram: String? = null,
    @SerializedName("linkedin")
    val linkedin: String? = null
)

data class UserDTO(
    @SerializedName("id_usuario")
    val idUsuario: String,
    @SerializedName("nombres")
    val nombres: String,
    @SerializedName("correo")
    val correo: String,
    @SerializedName("celular")
    val celular: String? = null,
    @SerializedName("foto")
    val foto: String? = null,
    @SerializedName("id_documento")
    val idDocumento: String? = null,
    @SerializedName("contrasena")
    val contrasena: String? = null,
    @SerializedName("roles")
    val redes: RedesDTO? = null,
    @SerializedName("tecnologias")
    val tecnologias: List<String>? = null,
    @SerializedName("proyectos")
    val proyectos: List<ProjectDTO>? = null
)
