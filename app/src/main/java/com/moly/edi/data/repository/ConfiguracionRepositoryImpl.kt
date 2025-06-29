
package com.moly.edi.data.repository

import android.util.Log
import com.moly.edi.data.dataSource.local.dao.ConfiguracionDao
import com.moly.edi.data.dataSource.remote.api.ConfiguracionApiService
import com.moly.edi.data.model.Configuracion
import com.moly.edi.data.model.ConfiguracionDTO
import com.google.gson.JsonSyntaxException
import com.moly.edi.domain.repository.ConfiguracionRepository
import kotlinx.coroutines.delay
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ConfiguracionRepositoryImpl @Inject constructor(
    private val api: ConfiguracionApiService,
    private val dao: ConfiguracionDao
) : ConfiguracionRepository {

    override suspend fun wakeUpServer(): Boolean {
        return try {
            api.getConfiguracionByUser("test")
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun syncConfiguracion(correoElectronico: String) {
        Log.d("ConfigSync", "Iniciando sincronización bidireccional para: $correoElectronico")

        try {
            // 1. SUBIR cambios locales pendientes PRIMERO
            val configuracionLocal = dao.getModifiedConfiguracion(correoElectronico)
            if (configuracionLocal != null) {
                Log.d("ConfigSync", "📤 Subiendo cambios locales al servidor...")
                val success = actualizarEnServidor(configuracionLocal)
                if (success) {
                    dao.markConfiguracionAsNotModified(correoElectronico)
                    dao.markConfiguracionAsSynced(correoElectronico)
                    Log.d("ConfigSync", "✅ Cambios locales subidos exitosamente")
                }
            }

            // 2. BAJAR configuración del servidor
            val result = getConfiguracionByUser(correoElectronico)
            result.fold(
                onSuccess = { dto ->
                    val configuracionServidor = mapDtoToEntity(dto, correoElectronico)

                    // Marcar como sincronizada y existente en servidor
                    val configuracionFinal = configuracionServidor.copy(
                        isSynced = true,
                        modifiedLocally = false,
                        existsInServer = true
                    )

                    dao.insertOrUpdateConfiguracion(configuracionFinal)
                    Log.d("ConfigSync", "✅ Configuración del servidor guardada localmente")
                },
                onFailure = { error ->
                    Log.e("ConfigSync", "❌ Error bajando del servidor: ${error.message}")

                    // Si no existe configuración local, crear una por defecto
                    val existeLocal = dao.configurationExists(correoElectronico) > 0
                    if (!existeLocal) {
                        val configPorDefecto = createDefaultConfiguracion(correoElectronico)
                        dao.insertOrUpdateConfiguracion(configPorDefecto)
                        Log.d("ConfigSync", "📝 Configuración por defecto creada")
                    }
                }
            )
        } catch (e: Exception) {
            Log.e("ConfigSync", "❌ Error en sincronización: ${e.message}")
            throw when (e) {
                is SocketTimeoutException, is UnknownHostException -> NetworkException()
                is JsonSyntaxException -> ApiException("Respuesta inválida del servidor")
                else -> SyncConflictException("Error durante sincronización: ${e.message}")
            }
        }
    }

    override suspend fun getConfiguracionByUserId(userId: String): Configuracion? {
        Log.d("ConfigRepository", "Obteniendo configuración para userId: $userId")
        return dao.getConfiguracionByUserId(userId)
    }

    override suspend fun insertOrUpdateConfiguracion(configuracion: Configuracion): Long {
        Log.d("ConfigRepository", "Insertando/actualizando configuración")
        return dao.insertOrUpdateConfiguracion(configuracion)
    }

    override suspend fun updateConfiguracion(configuracion: Configuracion): Int {
        Log.d("ConfigRepository", "Actualizando configuración local")

        // Marcar como modificada localmente para sincronización posterior
        val configuracionModificada = configuracion.copy(
            modifiedLocally = true,
            isSynced = false
        )

        return dao.updateConfiguracion(configuracionModificada)
    }

    override suspend fun getConfiguracionByUser(correoElectronico: String): Result<ConfiguracionDTO> {
        val url = "https://edi-backend-ww44.onrender.com/usuario/$correoElectronico/configuracion"
        Log.d("ConfigRepository", "Intentando obtener configuración en: $url")

        repeat(3) { attempt ->
            try {
                val response = api.getConfiguracionByUser(correoElectronico)

                return when (response.code()) {
                    200 -> {
                        response.body()?.let { dto ->
                            Log.d("ConfigRepository", "Configuración obtenida exitosamente")
                            Result.success(dto)
                        } ?: Result.failure(ApiException("JSON inválido"))
                    }
                    404 -> {
                        Log.w("ConfigRepository", "Usuario no encontrado")
                        Result.failure(UserNotFoundException())
                    }
                    500 -> {
                        Log.e("ConfigRepository", "Error del servidor")
                        Result.failure(ServerException())
                    }
                    503 -> {
                        Log.e("ConfigRepository", "Servicio no disponible")
                        Result.failure(ServiceUnavailableException())
                    }
                    else -> {
                        Log.e("ConfigRepository", "Error HTTP: ${response.code()}")
                        Result.failure(ApiException("Error HTTP: ${response.code()}"))
                    }
                }

            } catch (e: Exception) {
                val error = when (e) {
                    is SocketTimeoutException -> TimeoutException()
                    is UnknownHostException -> NetworkException()
                    is JsonSyntaxException -> ApiException("JSON inválido")
                    else -> NetworkException("Error de conexión")
                }

                Log.w("ConfigRepository", "Intento ${attempt + 1} falló: ${error.message}")

                if (attempt == 2) {
                    return Result.failure(error)
                }
                delay(2000)
            }
        }
        return Result.failure(NetworkException("Error después de 3 intentos"))
    }

    override suspend fun obtenerConfiguracion(correoElectronico: String): Configuracion {
        // 1. Intentar obtener de Room primero
        val configuracionLocal = dao.getConfiguracionByUserId(correoElectronico)
        if (configuracionLocal != null) {
            Log.d("ConfigRepository", "Configuración obtenida de Room")
            return configuracionLocal
        }

        // 2. Si no existe localmente, intentar del servidor
        val result = getConfiguracionByUser(correoElectronico)
        return result.fold(
            onSuccess = { dto ->
                val configuracion = mapDtoToEntity(dto, correoElectronico).copy(
                    isSynced = true,
                    existsInServer = true
                )
                dao.insertOrUpdateConfiguracion(configuracion)
                configuracion
            },
            onFailure = { error ->
                Log.e("ConfigRepository", "Error obteniendo configuración: ${error.message}")
                // Crear y guardar configuración por defecto
                val configPorDefecto = createDefaultConfiguracion(correoElectronico)
                dao.insertOrUpdateConfiguracion(configPorDefecto)
                configPorDefecto
            }
        )
    }

    override suspend fun actualizarConfiguracion(configuracion: Configuracion): Boolean {
        return try {
            // 1. Actualizar localmente primero
            val configuracionLocal = configuracion.copy(
                modifiedLocally = true,
                isSynced = false
            )
            dao.updateConfiguracion(configuracionLocal)

            // 2. Intentar actualizar en servidor
            val success = actualizarEnServidor(configuracion)
            if (success) {
                // Marcar como sincronizada
                dao.markConfiguracionAsNotModified(configuracion.idUsuario)
                dao.markConfiguracionAsSynced(configuracion.idUsuario)
                dao.markConfiguracionAsExistsInServer(configuracion.idUsuario)
                Log.d("ConfigRepository", "✅ Configuración actualizada en servidor")
            } else {
                Log.w("ConfigRepository", "⚠️ Configuración guardada localmente, pendiente de sincronizar")
            }

            true // Siempre retorna true porque al menos se guardó localmente

        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error actualizando configuración: ${e.message}")
            throw when (e) {
                is SocketTimeoutException, is UnknownHostException -> NetworkException()
                else -> SyncConflictException("Error actualizando: ${e.message}")
            }
        }
    }

    // ===== MÉTODOS PARA SINCRONIZACIÓN BIDIRECCIONAL =====

    override suspend fun sincronizarCambiosPendientes() {
        Log.d("ConfigRepository", "Sincronizando todos los cambios pendientes...")

        try {
            val configuracionesPendientes = dao.getAllModifiedConfigurations()

            for (config in configuracionesPendientes) {
                val success = actualizarEnServidor(config)
                if (success) {
                    dao.markConfiguracionAsNotModified(config.idUsuario)
                    dao.markConfiguracionAsSynced(config.idUsuario)
                    dao.markConfiguracionAsExistsInServer(config.idUsuario)
                    Log.d("ConfigRepository", "✅ Sincronizado: ${config.idUsuario}")
                } else {
                    Log.w("ConfigRepository", "⚠️ No se pudo sincronizar: ${config.idUsuario}")
                }
            }
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error sincronizando cambios pendientes: ${e.message}")
            throw SyncConflictException("Error sincronizando cambios: ${e.message}")
        }
    }

    override suspend fun tieneCambiosPendientes(userId: String): Boolean {
        return try {
            val config = dao.getModifiedConfiguracion(userId)
            config != null
        } catch (e: Exception) {
            Log.e("ConfigRepository", "Error verificando cambios pendientes: ${e.message}")
            false
        }
    }

    // ===== MÉTODOS AUXILIARES =====

    private suspend fun actualizarEnServidor(configuracion: Configuracion): Boolean {
        return try {
            val dto = mapEntityToDto(configuracion)
            val response = api.updateConfiguracion(configuracion.idUsuario, dto)

            when {
                response.isSuccessful -> {
                    Log.d("ConfigRepository", "✅ Actualización exitosa en servidor")
                    true
                }
                response.code() == 404 -> {
                    Log.w("ConfigRepository", "Usuario no encontrado en servidor")
                    throw UserNotFoundException()
                }
                response.code() >= 500 -> {
                    Log.e("ConfigRepository", "Error del servidor: ${response.code()}")
                    throw ServerException()
                }
                else -> {
                    Log.e("ConfigRepository", "Error HTTP: ${response.code()}")
                    false
                }
            }
        } catch (e: Exception) {
            when (e) {
                is ConfiguracionException -> throw e  // Re-lanzar excepciones conocidas
                is SocketTimeoutException, is UnknownHostException -> {
                    Log.e("ConfigRepository", "Error de red actualizando en servidor")
                    throw NetworkException()
                }
                else -> {
                    Log.e("ConfigRepository", "Error actualizando en servidor: ${e.message}")
                    false
                }
            }
        }
    }

    private fun mapDtoToEntity(dto: ConfiguracionDTO, correoElectronico: String): Configuracion {
        return Configuracion(
            idUsuario = dto.idUsuario ?: correoElectronico,
            notificacionesEnabled = dto.isNotificacion,
            visibilidadEnabled = dto.isVisibilidad,
            disponibilidadEnabled = dto.isDisponibilidad,
            isSynced = false,
            modifiedLocally = false,
            existsInServer = false
        )
    }

    private fun mapEntityToDto(configuracion: Configuracion): ConfiguracionDTO {
        return ConfiguracionDTO(
            idUsuario = configuracion.idUsuario,
            isNotificacion = configuracion.notificacionesEnabled,
            isVisibilidad = configuracion.visibilidadEnabled,
            isDisponibilidad = configuracion.disponibilidadEnabled
        )
    }

    private fun createDefaultConfiguracion(correoElectronico: String): Configuracion {
        return Configuracion(
            idUsuario = correoElectronico,
            notificacionesEnabled = true,
            visibilidadEnabled = true,
            disponibilidadEnabled = true,
            isSynced = false,
            modifiedLocally = false,
            existsInServer = false
        )
    }
}

// ===== EXCEPCIONES EN EL MISMO ARCHIVO (SOLUCIÓN MÁS SIMPLE) =====

sealed class ConfiguracionException(message: String) : Exception(message)

class NetworkException(message: String = "Sin conexión a internet") : ConfiguracionException(message)
class TimeoutException(message: String = "Tiempo de espera agotado") : ConfiguracionException(message)
class UserNotFoundException(message: String = "Usuario no encontrado") : ConfiguracionException(message)
class ServerException(message: String = "Error del servidor") : ConfiguracionException(message)
class ServiceUnavailableException(message: String = "Servicio no disponible") : ConfiguracionException(message)
class SyncConflictException(message: String = "Conflicto de sincronización") : ConfiguracionException(message)
class ApiException(message: String) : ConfiguracionException(message)