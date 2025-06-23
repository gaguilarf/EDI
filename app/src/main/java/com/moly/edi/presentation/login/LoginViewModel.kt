package com.moly.edi.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.core.auth.AuthPreferences
import com.moly.edi.data.model.AuthApiService
import com.moly.edi.data.model.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val authApiService: AuthApiService
) : AndroidViewModel(application) {
    private val authPrefs = AuthPreferences(application)
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Correo y contraseña no pueden estar vacíos."
            return
        }
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val response = authApiService.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body()?.usuario != null) {
                    authPrefs.setLoggedIn(true, email)
                    _loginSuccess.value = true
                } else {
                    val msg = response.body()?.message ?: "Credenciales incorrectas."
                    _errorMessage.value = msg
                }
            } catch (e: Exception) {
                android.util.Log.e("LoginViewModel", "Error de red o servidor", e)
                _errorMessage.value = "Error de red o servidor."
            } finally {
                _isLoading.value = false
            }
        }
    }
}