package com.moly.edi.presentation.configuracion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.domain.useCase.GetConfiguracionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

data class ConfiguracionUiState(
    val notificacionesEnabled: Boolean = false,
    val categoriesIntereses: List<String> = listOf("Tecnología", "Becas", "Prácticas"),
    val visibilidadEnabled: Boolean = false,
    val disponibilidadProyEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ConfiguracionViewModel(
    private val getConfiguracionUseCase: GetConfiguracionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfiguracionUiState())
    val uiState: StateFlow<ConfiguracionUiState> = _uiState.asStateFlow()

    fun loadConfiguracion(correoElectronico: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getConfiguracionUseCase(correoElectronico)
                .onSuccess { config ->
                    android.util.Log.d("ConfigViewModel", " Datos recibidos: $config")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,

                        notificacionesEnabled = config.notificacionesEnabled,
                        visibilidadEnabled = config.visibilidadEnabled,
                        disponibilidadProyEnabled = config.disponibilidadEnabled
                    )
                    android.util.Log.d("ConfigViewModel", " Estado actualizado: ${_uiState.value}")
                }
                .onFailure { error ->
                    // MENSAJES DE ERROR CLAROS
                    val userMessage = when {
                        error.message?.contains("Sin internet") == true ->
                            " Sin conexión a internet"
                        error.message?.contains("Usuario no encontrado") == true ->
                            " Usuario no encontrado"
                        error.message?.contains("Error del servidor") == true ->
                            " Servidor en mantenimiento"
                        error.message?.contains("JSON inválido") == true ->
                            " Datos corruptos del servidor"
                        else -> " Error inesperado"
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = userMessage
                    )
                }
        }
    }

    fun toggleNotificaciones() {
        _uiState.value = _uiState.value.copy(
            notificacionesEnabled = !_uiState.value.notificacionesEnabled
        )
    }

    fun toggleVisibilidad() {
        _uiState.value = _uiState.value.copy(
            visibilidadEnabled = !_uiState.value.visibilidadEnabled
        )
    }

    fun toggleDisponibilidadProy() {
        _uiState.value = _uiState.value.copy(
            disponibilidadProyEnabled = !_uiState.value.disponibilidadProyEnabled
        )
    }

    fun addCategoria(categoria: String) {
        val currentList = _uiState.value.categoriesIntereses.toMutableList()
        if (!currentList.contains(categoria)) {
            currentList.add(categoria)
            _uiState.value = _uiState.value.copy(categoriesIntereses = currentList)
        }
    }

    fun removeCategoria(categoria: String) {
        val currentList = _uiState.value.categoriesIntereses.toMutableList()
        currentList.remove(categoria)
        _uiState.value = _uiState.value.copy(categoriesIntereses = currentList)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
