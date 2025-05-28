package com.moly.edi.presentation.splash


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _welcomeText = MutableStateFlow("BIENVENIDO")
    val welcomeText: StateFlow<String> = _welcomeText

    private val _navigateToLogin = MutableStateFlow(false)
    val navigateToLogin: StateFlow<Boolean> = _navigateToLogin

    init {
        viewModelScope.launch {
            delay(3000)
            _navigateToLogin.value = true
        }
    }
}
