package com.moly.edi.domain.repository

import com.moly.edi.data.model.Configuracion
import com.moly.edi.data.model.ConfiguracionDTO

interface ConfiguracionRepository {
    // Métodos básicos
    suspend fun wakeUpServer(): Boolean
    suspend fun getConfiguracionByUser(correoElectronico: String): Result<ConfiguracionDTO>
    suspend fun obtenerConfiguracion(correoElectronico: String): Configuracion
    suspend fun actualizarConfiguracion(configuracion: Configuracion): Boolean

    // Métodos de Room
    suspend fun syncConfiguracion(correoElectronico: String)
    suspend fun getConfiguracionByUserId(userId: String): Configuracion?
    suspend fun insertOrUpdateConfiguracion(configuracion: Configuracion): Long
    suspend fun updateConfiguracion(configuracion: Configuracion): Int

    // Métodos para sincronización bidireccional
    suspend fun sincronizarCambiosPendientes()
    suspend fun tieneCambiosPendientes(userId: String): Boolean
}
