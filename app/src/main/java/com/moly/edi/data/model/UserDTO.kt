package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName
import com.moly.edi.domain.model.User

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

fun UserDTO.toDomain(): User {
    return User(
        id = this.idUsuario,
        nombre = this.nombres,
        correo = this.correo,
        telefono = this.celular,
        linkedin = this.redes?.linkedin,
        github = this.redes?.github,
        instagram = this.redes?.instagram,
        tecnologias = this.tecnologias ?: emptyList(),
        proyectos = this.proyectos?.map { it.toDomain() } ?: emptyList()
    )
}
