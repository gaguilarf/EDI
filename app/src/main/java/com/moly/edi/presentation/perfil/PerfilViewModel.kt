package com.moly.edi.presentation.perfil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.User
import com.moly.edi.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _technologies = MutableStateFlow<List<String>>(emptyList())
    val technologies: StateFlow<List<String>> = _technologies.asStateFlow()

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    fun loadUserData(email: String) {
        Log.d("PerfilViewModel", "Loading user data for email: $email")
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = userRepository.getUserByEmail(email)
                result.onSuccess { user ->
                    Log.d("PerfilViewModel", "User loaded successfully: $user")
                    _user.value = user
                    _technologies.value = user.tecnologias
                    _projects.value = user.proyectos
                }.onFailure { exception ->
                    Log.e("PerfilViewModel", "Failed to load user: ${exception.message}", exception)
                    _error.value = exception.message ?: "Error al cargar el perfil"
                }
            } catch (e: Exception) {
                Log.e("PerfilViewModel", "Exception loading user: ${e.message}", e)
                _error.value = e.message ?: "Error desconocido: ${e.javaClass.simpleName}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addProject(email: String, project: Project, onResult: (Boolean) -> Unit) {
        Log.d("PerfilViewModel", "[addProject] email: $email, project: $project")
        val projectWithId = if (project.id == "0" || project.id.isBlank()) {
            project.copy(id = UUID.randomUUID().toString())
        } else project
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("PerfilViewModel", "[addProject] Llamando a userRepository.addProjectToUser...")
                val result = userRepository.addProjectToUser(email, projectWithId)
                Log.d("PerfilViewModel", "[addProject] Resultado de addProjectToUser: $result")
                if (result.isSuccess) {
                    Log.d("PerfilViewModel", "[addProject] Proyecto agregado remotamente. Recargando usuario...")
                    loadUserData(email)
                    onResult(true)
                } else {
                    Log.e("PerfilViewModel", "[addProject] Error al guardar el proyecto: ${result.exceptionOrNull()?.message}")
                    _error.value = result.exceptionOrNull()?.message ?: "Error al guardar el proyecto"
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("PerfilViewModel", "[addProject] Excepción: ${e.message}", e)
                _error.value = e.message ?: "Error desconocido al guardar proyecto"
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }
}