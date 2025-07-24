package com.moly.edi.presentation.conecta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.domain.model.User
import com.moly.edi.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfilConectaViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _perfil = MutableStateFlow<User?>(null)
    val perfil: StateFlow<User?> = _perfil.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cargarPerfil(correoElectronico: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                // Por ahora, creamos un perfil de ejemplo
                // En una implementación real, obtendrías los datos del repository
                val perfilEjemplo = User(
                    id = "1",
                    nombre = "Usuario Ejemplo",
                    correo = correoElectronico,
                    celular = "+51 999 999 999",
                    linkedin = "linkedin.com/in/usuario-ejemplo",
                    instagram = "@usuario_ejemplo",
                    competencias = "Kotlin, Android, Jetpack Compose, Firebase",
                    proyectos = listOf(
                        com.moly.edi.domain.model.Project(
                            id = "1",
                            userId = "1",
                            titulo = "App EDI",
                            descripcion = "Aplicación móvil para conectar estudiantes de ingeniería"
                        ),
                        com.moly.edi.domain.model.Project(
                            id = "2",
                            userId = "1",
                            titulo = "Sistema de Gestión",
                            descripcion = "Sistema web para gestión de proyectos"
                        )
                    )
                )
                
                _perfil.value = perfilEjemplo
            } catch (e: Exception) {
                _error.value = "Error al cargar el perfil: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
} 