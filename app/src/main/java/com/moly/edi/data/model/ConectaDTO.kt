package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName

data class ConectaDTO(
    @SerializedName("carrera")
    val carrera: String,
    @SerializedName("competencias")
    val competencias: String? = null,
    @SerializedName("nombres")
    val nombres: String? = null,
    @SerializedName("semestre")
    val semestre: Int,
    @SerializedName("sobre_mi")
    val sobre_mi: String
)