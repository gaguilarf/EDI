package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName
import com.moly.edi.domain.model.User

data class RolesDTO(
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
    val roles: RolesDTO? = null
)

fun UserDTO.toDomain(): User {
    return User(
        id = idUsuario.hashCode(), // Generamos un ID numérico
        nombre = nombres,
        correo = correo,
        telefono = celular,
        linkedin = roles?.linkedin,
        github = roles?.github,
        instagram = roles?.instagram,
        tecnologias = emptyList(), // Por ahora vacío, se puede llenar más tarde
        proyectos = emptyList() // Por ahora vacío, se puede llenar más tarde
    )
}
