package com.moly.edi.domain.model

data class Configuracion(
    val idUsuario: String?, // <- NULLABLE
    val notificacionesEnabled: Boolean,
    val visibilidadEnabled: Boolean,
    val disponibilidadEnabled: Boolean
)