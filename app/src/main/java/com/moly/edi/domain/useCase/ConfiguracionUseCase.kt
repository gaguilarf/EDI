package com.moly.edi.domain.useCase


import com.moly.edi.data.model.ConfiguracionDTO
import com.moly.edi.data.repository.ConfiguracionRepository
import com.moly.edi.domain.model.Configuracion


class GetConfiguracionUseCase(
    private val repository: ConfiguracionRepository
) {
    suspend operator fun invoke(correoElectronico: String): Result<Configuracion> {
        return repository.getConfiguracionByUser(correoElectronico)
            .map { dto -> dto.toModel() }
    }
}

// Extension function para convertir DTO a Model
fun ConfiguracionDTO.toModel(): Configuracion {
    return Configuracion(
        idUsuario = this.idUsuario, // Puede ser null, no hay problema
        notificacionesEnabled = this.isNotificacion,
        visibilidadEnabled = this.isVisibilidad,
        disponibilidadEnabled = this.isDisponibilidad
    )
}