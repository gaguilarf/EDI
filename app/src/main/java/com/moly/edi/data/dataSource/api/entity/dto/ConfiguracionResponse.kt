package com.moly.edi.data.dataSource.api.entity.dto

data class ConfiguracionResponse(
    val success: Boolean,
    val data: ConfiguracionDTO?,
    val message: String?
)