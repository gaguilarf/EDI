package com.moly.edi.data.repository

import android.util.Log
import com.moly.edi.data.dataSource.remote.api.ConectaService
import com.moly.edi.data.mapper.toDomain
import com.moly.edi.domain.model.Conecta
import com.moly.edi.domain.repository.ConectaRepository
import java.net.URLEncoder
import javax.inject.Inject

class ConectaRepositoryImpl @Inject constructor(
    private val api: ConectaService
) : ConectaRepository {

    override suspend fun getConectaByEmail(email: String): Result<Conecta> {
        return try {
            val encodedEmail = URLEncoder.encode(email, "UTF-8")
            Log.d("ConectaRepository", "Intentando obtener datos para email: $email (encoded: $encodedEmail)")
            val response = api.getConectaByEmail(encodedEmail)
            Log.d("ConectaRepository", "Respuesta del servidor: ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                Log.d("ConectaRepository", "Datos obtenidos exitosamente")
                Result.success(response.body()!!.toDomain())
            } else {
                val errorMsg = "Error al obtener datos de Conecta: ${response.code()}"
                Log.e("ConectaRepository", errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("ConectaRepository", "Excepción al obtener datos", e)
            Result.failure(e)
        }
    }

    suspend fun testServer(): Result<Conecta> {
        return try {
            Log.d("ConectaRepository", "Probando endpoint de prueba...")
            val response = api.testEndpoint()
            Log.d("ConectaRepository", "Respuesta del servidor (test): ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                Log.d("ConectaRepository", "Test exitoso")
                Result.success(response.body()!!.toDomain())
            } else {
                val errorMsg = "Error en test: ${response.code()}"
                Log.e("ConectaRepository", errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("ConectaRepository", "Excepción en test", e)
            Result.failure(e)
        }
    }
}