package com.moly.edi.presentacion.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

data class RegisterUiState(
    val nombre: String = "",
    val email: String = "",
    val contrasena: String = "",
    val linkedin: String = "",
    val github: String = "",
    val numeroCelular: String = "",
    val instagram: String = "",
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val contrasenaError: String? = null,
    val numeroCelularError: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    // Patrones de validación
    private val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private val phonePattern = Pattern.compile("^9[0-9]{8}$")

    fun updateNombre(nombre: String) {
        _uiState.value = _uiState.value.copy(nombre = nombre)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = null
        )
    }

    fun updateContrasena(contrasena: String) {
        _uiState.value = _uiState.value.copy(
            contrasena = contrasena,
            contrasenaError = null
        )
    }

    fun updateLinkedin(linkedin: String) {
        _uiState.value = _uiState.value.copy(linkedin = linkedin)
    }

    fun updateGithub(github: String) {
        _uiState.value = _uiState.value.copy(github = github)
    }

    fun updateNumeroCelular(numeroCelular: String) {
        // Filtrar solo números
        val filteredNumber = numeroCelular.filter { it.isDigit() }
        _uiState.value = _uiState.value.copy(
            numeroCelular = filteredNumber,
            numeroCelularError = null
        )
    }

    fun updateInstagram(instagram: String) {
        _uiState.value = _uiState.value.copy(instagram = instagram)
    }

    fun crearCuenta() {
        if (!validateForm()) {
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Simular llamada a API
                kotlinx.coroutines.delay(2000)

                // Simular respuesta exitosa (aquí iría la lógica real de registro)
                val registroExitoso = registrarUsuario()

                if (registroExitoso) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Cuenta creada exitosamente"
                    )
                    limpiarFormulario()
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Error al crear la cuenta. Inténtalo de nuevo."
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error de conexión. Verifica tu internet."
                )
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        val currentState = _uiState.value

        // Validar email
        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(emailError = "Email es requerido")
            isValid = false
        } else if (!emailPattern.matcher(currentState.email).matches()) {
            _uiState.value = currentState.copy(emailError = "Email no válido")
            isValid = false
        }

        // Validar contraseña
        if (currentState.contrasena.isBlank()) {
            _uiState.value = _uiState.value.copy(contrasenaError = "Contraseña es requerida")
            isValid = false
        } else if (currentState.contrasena.length < 6) {
            _uiState.value = _uiState.value.copy(contrasenaError = "Contraseña debe tener al menos 6 caracteres")
            isValid = false
        }

        // Validar número celular
        if (currentState.numeroCelular.isBlank()) {
            _uiState.value = _uiState.value.copy(numeroCelularError = "Número celular es requerido")
            isValid = false
        } else if (!phonePattern.matcher(currentState.numeroCelular).matches()) {
            _uiState.value = _uiState.value.copy(numeroCelularError = "Número debe empezar con 9 y tener 9 dígitos")
            isValid = false
        }

        return isValid
    }

    private suspend fun registrarUsuario(): Boolean {
        // Aquí iría la lógica real para registrar el usuario
        // Por ejemplo, llamada a un repositorio o API

        // Simulamos una probabilidad de éxito del 90%
        return (1..10).random() <= 9
    }

    private fun limpiarFormulario() {
        _uiState.value = RegisterUiState()
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }

    // Métodos adicionales para obtener datos específicos (componentes UI para visualizar datos)
    fun getFormProgress(): Float {
        val totalFields = 7f // Total de campos en el formulario
        val completedFields = listOf(
            _uiState.value.nombre,
            _uiState.value.email,
            _uiState.value.contrasena,
            _uiState.value.linkedin,
            _uiState.value.github,
            _uiState.value.numeroCelular,
            _uiState.value.instagram
        ).count { it.isNotBlank() }

        return completedFields / totalFields
    }

    fun getRequiredFieldsStatus(): Map<String, Boolean> {
        return mapOf(
            "email" to _uiState.value.email.isNotBlank(),
            "contrasena" to _uiState.value.contrasena.isNotBlank(),
            "numeroCelular" to _uiState.value.numeroCelular.isNotBlank()
        )
    }

    fun isFormValid(): Boolean {
        val state = _uiState.value
        return state.email.isNotBlank() &&
                emailPattern.matcher(state.email).matches() &&
                state.contrasena.length >= 6 &&
                state.numeroCelular.isNotBlank() &&
                phonePattern.matcher(state.numeroCelular).matches()
    }

    init {
        // Datos de prueba para visualizar en la UI
        _uiState.value = RegisterUiState(
            nombre = "Juan Pérez",
            email = "juan@example.com",
            contrasena = "123456",
            linkedin = "linkedin.com/in/juan",
            github = "github.com/juan",
            numeroCelular = "912345678",
            instagram = "@juan.ig"
        )
    }
}