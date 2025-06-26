package com.moly.edi.data.model

data class ConfiguracionResponse(
    val success: Boolean,
    val data: ConfiguracionDTO?,
    val message: String?
)