package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName

data class CompleteUserDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("nombres")
    val nombres: String,
    @SerializedName("correo")
    val correo: String,
    @SerializedName("celular")
    val celular: String? = null,
    @SerializedName("foto")
    val foto: String? = null,
    @SerializedName("contrasenia")
    val contrasena: String? = null,
    @SerializedName("redes")
    val redes: RedesDTO? = null,
    @SerializedName("competencias")
    val competencias: List<String>? = null,
    @SerializedName("proyectos")
    val proyectos: List<ProjectDTO>? = null,
    @SerializedName("carrera")
    val carrera: String? = null,
    @SerializedName("semestre")
    val semestre: Int,
    @SerializedName("sobre_mi")
    val sobre_mi: String? = null,
)