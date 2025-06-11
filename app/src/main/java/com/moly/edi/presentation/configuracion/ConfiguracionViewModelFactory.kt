package com.moly.edi.presentation.configuracion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moly.edi.domain.useCase.GetConfiguracionUseCase

class ConfiguracionViewModelFactory(
    private val getConfiguracionUseCase: GetConfiguracionUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfiguracionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConfiguracionViewModel(getConfiguracionUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
