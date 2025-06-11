package com.moly.edi.presentation.configuracion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.domain.useCase.GetConfiguracionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ConfiguracionUiState(
    val notificacionesEnabled: Boolean = true,
    val categoriesIntereses: List<String> = listOf("Tecnología", "Becas", "Prácticas"),
    val visibilidadEnabled: Boolean = true,
    val disponibilidadProyEnabled: Boolean = true,
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
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            getConfiguracionUseCase(correoElectronico)
                .onSuccess { configuracion ->
                    _uiState.value = _uiState.value.copy(
                        notificacionesEnabled = configuracion.notificacionesEnabled,
                        visibilidadEnabled = configuracion.visibilidadEnabled,
                        disponibilidadProyEnabled = configuracion.disponibilidadEnabled,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message
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
