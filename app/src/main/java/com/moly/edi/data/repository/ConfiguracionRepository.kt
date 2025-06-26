package com.moly.edi.data.repository
import com.moly.edi.data.model.ConfiguracionDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.delay
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ConfiguracionApiService {
    @GET("usuario/{correo_electronico}/configuracion")
    suspend fun getConfiguracionByUser(
        @Path("correo_electronico") correoElectronico: String
    ): Response<ConfiguracionDTO>
}
class ConfiguracionRepository(
    private val apiService: ConfiguracionApiService
) {
    suspend fun wakeUpServer(): Boolean {
        return try {
            apiService.getConfiguracionByUser("test") // Llamada de prueba
            true
        } catch (e: Exception) {
            false
        }
    }
    suspend fun getConfiguracionByUser(correoElectronico: String): Result<ConfiguracionDTO> {
        val url = "https://edi-backend-ww44.onrender.com/usuario/$correoElectronico/configuracion"
        android.util.Log.d("ConfigRepository", "Intentando obtener configuración en: $url")
        // REINTENTOS (3 intentos)
        repeat(3) { attempt ->
            try {
                val response = apiService.getConfiguracionByUser(correoElectronico)

                // MANEJO DE CÓDIGOS HTTP
                return when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Result.success(it)
                        } ?: Result.failure(ApiException("JSON inválido"))
                    }
                    404 -> Result.failure(ApiException("Usuario no encontrado"))
                    500 -> Result.failure(ApiException("Error del servidor"))
                    else -> Result.failure(ApiException("Error HTTP: ${response.code()}"))
                }

            } catch (e: Exception) {
                // FALLOS COMUNES
                val errorMessage = when (e) {
                    is SocketTimeoutException, is UnknownHostException -> "Sin internet"
                    is JsonSyntaxException -> "JSON inválido"
                    else -> "Error de conexión"
                }

                if (attempt == 2) { // Último intento
                    return Result.failure(ApiException(errorMessage))
                }
                delay(2000) // Esperar antes de reintentar
            }
        }
        return Result.failure(ApiException("Error después de 3 intentos"))
    }
}

class ApiException(message: String) : Exception(message)