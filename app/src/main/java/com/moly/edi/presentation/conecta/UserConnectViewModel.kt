package com.moly.edi.presentation.conecta

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.data.model.ConectaDTO
import com.moly.edi.data.repository.ConectaRepositoryImpl
import com.moly.edi.domain.model.Conecta
import com.moly.edi.domain.repository.ConectaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserConnectViewModel @Inject constructor(
    private val conectaRepository: ConectaRepository
) : ViewModel() {

    var estudiante by mutableStateOf<ConectaDTO?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var currentEmail: String = ""

    fun loadEstudiante(correo: String) {
        Log.d("UserConnectViewModel", "Cargando estudiante para correo: $correo")
        currentEmail = correo
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                Log.d("UserConnectViewModel", "Llamando al repository...")
                val result = conectaRepository.getConectaByEmail(correo)
                result.fold(
                    onSuccess = { conecta ->
                        Log.d("UserConnectViewModel", "Datos obtenidos exitosamente")
                        estudiante = conecta.toDTO(correo)
                    },
                    onFailure = { exception ->
                        Log.e("UserConnectViewModel", "Error al obtener datos", exception)
                        errorMessage = exception.message ?: "Error desconocido"
                    }
                )
            } catch (e: Exception) {
                Log.e("UserConnectViewModel", "Excepción en loadEstudiante", e)
                errorMessage = e.message ?: "Error al cargar datos"
            } finally {
                isLoading = false
            }
        }
    }

    fun testServer() {
        Log.d("UserConnectViewModel", "Probando servidor...")
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val result = (conectaRepository as ConectaRepositoryImpl).testServer()
                result.fold(
                    onSuccess = { conecta ->
                        Log.d("UserConnectViewModel", "Test exitoso")
                        errorMessage = "Test exitoso - Servidor funcionando"
                    },
                    onFailure = { exception ->
                        Log.e("UserConnectViewModel", "Error en test", exception)
                        errorMessage = "Test falló: ${exception.message}"
                    }
                )
            } catch (e: Exception) {
                Log.e("UserConnectViewModel", "Excepción en test", e)
                errorMessage = "Error en test: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun Conecta.toDTO(correo: String): ConectaDTO {
        return ConectaDTO(
            nombres = this.nombres ?: "",
            carrera = this.carrera,
            palabras_clave = this.competencias,
            semestre = this.semestre,
            sobre_mi = this.sobre_mi
        )
    }
}
