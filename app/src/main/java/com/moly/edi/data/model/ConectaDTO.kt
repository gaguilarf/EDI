package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName

data class ConectaDTO(
    @SerializedName("nombres")
    val nombres: String,
    @SerializedName("carrera")
    val carrera: String,
    @SerializedName("palabras_clave")
    val palabras_clave: String? = null,
    @SerializedName("semestre")
    val semestre: Int,
    @SerializedName("sobre_mi")
    val sobre_mi: String,
    @SerializedName("aptitudes")
    val aptitudes: List<String>? = null,
    @SerializedName("celular")
    val celular: String? = null
) {
    // Propiedad computada para mantener compatibilidad
    val competencias: String? get() = palabras_clave
}