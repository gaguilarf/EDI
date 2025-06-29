
package com.moly.edi.presentation.configuracion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.data.model.Configuracion
import com.moly.edi.domain.repository.ConfiguracionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ConfiguracionViewModel @Inject constructor(
    private val repository: ConfiguracionRepository
) : ViewModel() {

    // ===== ESTADO DE LA CONFIGURACIÓN =====
    private val _configuracion = MutableStateFlow<Configuracion?>(null)
    val configuracion: StateFlow<Configuracion?> = _configuracion.asStateFlow()

    // ===== ESTADO DE CARGA =====
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ===== ESTADO DE ERROR =====
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // ===== ESTADO DE SINCRONIZACIÓN =====
    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    // ===== ESTADO DE CONEXIÓN =====
    private val _isOffline = MutableStateFlow(false)
    val isOffline: StateFlow<Boolean> = _isOffline.asStateFlow()

    // ===== ESTADO DE CAMBIOS PENDIENTES =====
    private val _hasPendingChanges = MutableStateFlow(false)
    val hasPendingChanges: StateFlow<Boolean> = _hasPendingChanges.asStateFlow()

    // ===== INICIALIZACIÓN (ÚNICO MÉTODO) =====
    fun inicializar(correoElectronico: String) {
        Log.d("ConfigViewModel", "Inicializando ViewModel para: $correoElectronico")

        // 1. Cargar configuración (local primero, luego servidor)
        cargarConfiguracion(correoElectronico)

        // 2. Despertar servidor en background
        viewModelScope.launch {
            try {
                val serverDespierto = repository.wakeUpServer()
                if (serverDespierto) {
                    _isOffline.value = false
                    // 3. Verificar si hay cambios pendientes
                    val tienePendientes = repository.tieneCambiosPendientes(correoElectronico)
                    if (tienePendientes) {
                        _hasPendingChanges.value = true
                        sincronizarCambiosPendientes()
                    } else {
                        // Solo sincronizar configuración del servidor
                        sincronizarConfiguracion(correoElectronico)
                    }
                } else {
                    _isOffline.value = true
                }
            } catch (e: Exception) {
                Log.w("ConfigViewModel", "Inicialización en background falló: ${e.message}")
                _isOffline.value = true
            }
        }
    }

    // ===== CARGAR CONFIGURACIÓN =====
    fun cargarConfiguracion(correoElectronico: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                Log.d("ConfigViewModel", "Cargando configuración para: $correoElectronico")

                val config = repository.obtenerConfiguracion(correoElectronico)
                _configuracion.value = config

                // Verificar si hay cambios pendientes
                val tienePendientes = repository.tieneCambiosPendientes(correoElectronico)
                _hasPendingChanges.value = tienePendientes

                Log.d("ConfigViewModel", "Configuración cargada: $config")

            } catch (e: Exception) {
                Log.e("ConfigViewModel", "Error cargando configuración: ${e.message}")
                handleError(e, "cargar configuración")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ===== ACTUALIZAR CONFIGURACIÓN =====
    fun actualizarConfiguracion(nuevaConfiguracion: Configuracion) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                Log.d("ConfigViewModel", "Actualizando configuración: $nuevaConfiguracion")

                val success = repository.actualizarConfiguracion(nuevaConfiguracion)

                if (success) {
                    _configuracion.value = nuevaConfiguracion
                    _hasPendingChanges.value = false
                    _isOffline.value = false
                    Log.d("ConfigViewModel", " Configuración actualizada exitosamente")
                } else {
                    _errorMessage.value = "No se pudo actualizar la configuración"
                    Log.e("ConfigViewModel", " Falló la actualización")
                }

            } catch (e: Exception) {
                Log.e("ConfigViewModel", "Error actualizando configuración: ${e.message}")
                handleError(e, "actualizar configuración")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ===== SINCRONIZAR CON SERVIDOR =====
    fun sincronizarConfiguracion(correoElectronico: String) {
        viewModelScope.launch {
            try {
                _isSyncing.value = true
                _errorMessage.value = null

                Log.d("ConfigViewModel", "Sincronizando configuración...")

                repository.syncConfiguracion(correoElectronico)

                // Recargar configuración después de sincronizar
                val config = repository.obtenerConfiguracion(correoElectronico)
                _configuracion.value = config
                _hasPendingChanges.value = false
                _isOffline.value = false

                Log.d("ConfigViewModel", " Sincronización completada")

            } catch (e: Exception) {
                Log.e("ConfigViewModel", "Error en sincronización: ${e.message}")
                handleError(e, "sincronizar")
            } finally {
                _isSyncing.value = false
            }
        }
    }

    // ===== ACTUALIZAR CONFIGURACIÓN LOCAL =====
    fun actualizarConfiguracionLocal(configuracion: Configuracion) {
        viewModelScope.launch {
            try {
                Log.d("ConfigViewModel", "Actualizando configuración local")

                val success = repository.updateConfiguracion(configuracion) > 0

                if (success) {
                    _configuracion.value = configuracion
                    _hasPendingChanges.value = true // Marcar como pendiente
                    Log.d("ConfigViewModel", "Configuración local actualizada")
                } else {
                    _errorMessage.value = "Error al guardar cambios localmente"
                }

            } catch (e: Exception) {
                Log.e("ConfigViewModel", "Error actualizando local: ${e.message}")
                _errorMessage.value = "Error al guardar localmente: ${e.message}"
            }
        }
    }

    // ===== SINCRONIZAR CAMBIOS PENDIENTES =====
    fun sincronizarCambiosPendientes() {
        viewModelScope.launch {
            try {
                _isSyncing.value = true
                _errorMessage.value = null

                Log.d("ConfigViewModel", "Sincronizando cambios pendientes...")

                repository.sincronizarCambiosPendientes()
                _hasPendingChanges.value = false
                _isOffline.value = false

                Log.d("ConfigViewModel", "✅ Todos los cambios sincronizados")

            } catch (e: Exception) {
                Log.e("ConfigViewModel", "Error sincronizando pendientes: ${e.message}")
                handleError(e, "sincronizar cambios pendientes")
            } finally {
                _isSyncing.value = false
            }
        }
    }

    // ===== DESPERTAR SERVIDOR =====
    fun despertarServidor() {
        viewModelScope.launch {
            try {
                Log.d("ConfigViewModel", "Despertando servidor...")

                val success = repository.wakeUpServer()

                if (success) {
                    _isOffline.value = false
                    Log.d("ConfigViewModel", "✅ Servidor despierto")
                } else {
                    _isOffline.value = true
                    Log.w("ConfigViewModel", "⚠️ No se pudo despertar servidor")
                }

            } catch (e: Exception) {
                Log.e("ConfigViewModel", "Error despertando servidor: ${e.message}")
                _isOffline.value = true
            }
        }
    }

    // ===== MÉTODOS DE CONFIGURACIÓN ESPECÍFICOS =====

    fun toggleNotificaciones(correoElectronico: String) {
        _configuracion.value?.let { config ->
            val nuevaConfig = config.copy(
                notificacionesEnabled = !config.notificacionesEnabled
            )
            actualizarConfiguracionLocal(nuevaConfig)
        }
    }

    fun toggleVisibilidad(correoElectronico: String) {
        _configuracion.value?.let { config ->
            val nuevaConfig = config.copy(
                visibilidadEnabled = !config.visibilidadEnabled
            )
            actualizarConfiguracionLocal(nuevaConfig)
        }
    }

    fun toggleDisponibilidad(correoElectronico: String) {
        _configuracion.value?.let { config ->
            val nuevaConfig = config.copy(
                disponibilidadEnabled = !config.disponibilidadEnabled
            )
            actualizarConfiguracionLocal(nuevaConfig)
        }
    }

    // ===== GUARDAR CAMBIOS EN SERVIDOR =====
    fun guardarCambiosEnServidor() {
        _configuracion.value?.let { config ->
            actualizarConfiguracion(config)
        }
    }

    // ===== REINTENTAR OPERACIONES =====
    fun reintentarSincronizacion(correoElectronico: String) {
        if (!_isSyncing.value) {
            sincronizarConfiguracion(correoElectronico)
        }
    }

    fun reintentarGuardar() {
        if (!_isLoading.value) {
            guardarCambiosEnServidor()
        }
    }

    // ===== LIMPIAR ERRORES =====
    fun limpiarError() {
        _errorMessage.value = null
    }

    // ===== GESTIÓN DE CONECTIVIDAD (ÚNICO MÉTODO) =====
    fun onNetworkAvailable() {
        Log.d("ConfigViewModel", "Red disponible, sincronizando...")
        _isOffline.value = false

        _configuracion.value?.idUsuario?.let { userId ->
            // Sincronizar cambios pendientes automáticamente
            if (_hasPendingChanges.value) {
                sincronizarCambiosPendientes()
            } else {
                // Luego sincronizar configuración del servidor
                sincronizarConfiguracion(userId)
            }
        }
    }

    fun onNetworkLost() {
        Log.d("ConfigViewModel", "Red perdida")
        _isOffline.value = true
    }

    // ===== MANEJO DE ERRORES (SIN IMPORTS DE EXCEPCIONES ESPECÍFICAS) =====
    private fun handleError(exception: Exception, operacion: String) {
        val mensaje = when {
            exception.message?.contains("Sin conexión") == true ||
                    exception.message?.contains("Sin internet") == true ||
                    exception is UnknownHostException -> {
                _isOffline.value = true
                "Sin conexión a internet. Los cambios se guardarán localmente."
            }
            exception.message?.contains("Tiempo de espera") == true ||
                    exception is SocketTimeoutException -> {
                _isOffline.value = true
                "El servidor tardó demasiado en responder. Intenta más tarde."
            }
            exception.message?.contains("Usuario no encontrado") == true -> {
                "Usuario no encontrado. Verifica tu cuenta."
            }
            exception.message?.contains("Error del servidor") == true ||
                    exception.message?.contains("500") == true -> {
                "Error en el servidor. Intenta más tarde."
            }
            exception.message?.contains("Servicio no disponible") == true ||
                    exception.message?.contains("503") == true -> {
                _isOffline.value = true
                "Servicio temporalmente no disponible. Intenta más tarde."
            }
            exception.message?.contains("Conflicto") == true -> {
                "Conflicto de sincronización. Intentando resolver..."
            }
            else -> {
                Log.w("ConfigViewModel", "Error no categorizado: ${exception::class.simpleName}")
                "Error al $operacion: ${exception.message}"
            }
        }

        _errorMessage.value = mensaje
        Log.e("ConfigViewModel", "Error en $operacion: ${exception.message}")
    }
}