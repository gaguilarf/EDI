package com.moly.edi.domain.useCase


import com.moly.edi.data.dataSource.api.entity.dto.ConfiguracionDTO
import com.moly.edi.data.dataSource.api.entity.dto.ConfiguracionRepository
import com.moly.edi.domain.model.ConfiguracionModel


class GetConfiguracionUseCase(
    private val repository: ConfiguracionRepository
) {
    suspend operator fun invoke(correoElectronico: String): Result<ConfiguracionModel> {
        return repository.getConfiguracionByUser(correoElectronico)
            .map { dto -> dto.toModel() }
    }
}

// Extension function para convertir DTO a Model
fun ConfiguracionDTO.toModel(): ConfiguracionModel {
    return ConfiguracionModel(
        idUsuario = this.idUsuario, // Puede ser null, no hay problema
        notificacionesEnabled = this.isNotificacion,
        visibilidadEnabled = this.isVisibilidad,
        disponibilidadEnabled = this.isDisponibilidad
    )
}