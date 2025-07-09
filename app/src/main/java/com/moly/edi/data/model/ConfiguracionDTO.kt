
package com.moly.edi.data.model

import com.google.gson.annotations.SerializedName

data class ConfiguracionDTO(
    @SerializedName("id_usuario")
    val idUsuario: String?, // <- NULLABLE para manejar null de la API

    @SerializedName("is_disponibilidad")
    val isDisponibilidad: Boolean,

    @SerializedName("is_notificacion")
    val isNotificacion: Boolean,

    @SerializedName("is_visibilidad")
    val isVisibilidad: Boolean,

    @SerializedName("categorias_interes")
    val categoriasInteres: List<String> = emptyList()
)