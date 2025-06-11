package com.moly.edi.data.dataSource.api.entity.dto
import com.moly.edi.data.dataSource.api.entity.dto.ConfiguracionDTO
import com.moly.edi.data.dataSource.api.entity.dto.ConfiguracionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import android.util.Log

interface ConfiguracionApiService {
    @GET("usuario/{correo_electronico}/configuracion")
    suspend fun getConfiguracionByUser(
        @Path("correo_electronico") correoElectronico: String
    ): Response<ConfiguracionDTO>
}
class ConfiguracionRepository(
    private val apiService: ConfiguracionApiService
) {
    suspend fun getConfiguracionByUser(correoElectronico: String): Result<ConfiguracionDTO> {
        return try {
            Log.d("ConfigRepo", " Cargando configuración para: $correoElectronico")

            val response = apiService.getConfiguracionByUser(correoElectronico)

            Log.d("ConfigRepo", " Response code: ${response.code()}")
            Log.d("ConfigRepo", " Response message: ${response.message()}")
            Log.d("ConfigRepo", " Response body: ${response.body()}")

            if (response.isSuccessful) {
                response.body()?.let { configuracion ->
                    Log.d("ConfigRepo", " Configuración cargada exitosamente: $configuracion")
                    Result.success(configuracion)
                } ?: run {
                    Log.e("ConfigRepo", "Response body es null")
                    Result.failure(Exception("Configuración no encontrada"))
                }
            } else {
                Log.e("ConfigRepo", " Error HTTP: ${response.code()} - ${response.message()}")
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("ConfigRepo", " Excepción: ${e.message}", e)
            Result.failure(e)
        }
    }
}