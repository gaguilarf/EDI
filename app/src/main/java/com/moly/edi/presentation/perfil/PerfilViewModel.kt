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
}