package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.User

data class RedesDTO(
    @SerializedName("github")
    val github: String? = null,
    @SerializedName("instagram")
    val instagram: String? = null,
    @SerializedName("linkedin")
    val linkedin: String? = null
)

data class ProjectDTO(
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("descripcion")
    val descripcion: String
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
    val roles: RedesDTO? = null,
    @SerializedName("tecnologias")
    val tecnologias: List<String>? = null,
    @SerializedName("proyectos")
    val proyectos: List<ProjectDTO>? = null
)

fun ProjectDTO.toDomain(): Project {
    return Project(
        id = titulo.hashCode(), // O usa otro identificador único si lo tienes
        titulo = titulo,
        descripcion = descripcion
    )
}

fun UserDTO.toDomain(): User {
    return User(
        id = idUsuario.hashCode(),
        nombre = nombres,
        correo = correo,
        telefono = celular,
        linkedin = roles?.linkedin,
        github = roles?.github,
        instagram = roles?.instagram,
        tecnologias = tecnologias ?: emptyList(),
        proyectos = proyectos?.map { it.toDomain() } ?: emptyList()
    )
}
