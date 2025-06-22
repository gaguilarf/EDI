package com.moly.edi.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.core.auth.AuthPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val authPrefs = AuthPreferences(application)
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    fun login(email: String, password: String) {
        // Aquí iría la lógica real de autenticación
        // Si es exitosa:
        viewModelScope.launch {
            authPrefs.setLoggedIn(true)
            _loginSuccess.value = true
        }
    }
}