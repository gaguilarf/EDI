package com.moly.edi.data.repository

import android.util.Log
import com.moly.edi.data.dataSource.remote.api.UserApiService
import com.moly.edi.data.mapper.toDomain
import com.moly.edi.data.model.ConectaDTO
import com.moly.edi.domain.model.Conecta
import com.moly.edi.domain.repository.ConectaRepository
import javax.inject.Inject

class ConectaRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : ConectaRepository {

    override suspend fun getConectaByEmail(email: String): Result<Conecta> {
        return try {
            Log.d("ConectaRepository", "Obteniendo datos de usuario para: $email")
            val response = userApiService.getUserByEmail(email)
            Log.d("ConectaRepository", "Respuesta del servidor: ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                val userDTO = response.body()!!
                Log.d("ConectaRepository", "Datos obtenidos exitosamente")

                // Convertir UserDTO a ConectaDTO
                val conectaDTO = ConectaDTO(
                    nombres = userDTO.nombres,
                    carrera = "Ingeniería de Sistemas", // Valor por defecto temporal
                    palabras_clave = userDTO.competencias,
                    semestre = 8, // Valor por defecto temporal
                    sobre_mi = "Estudiante de ${userDTO.nombres}", // Valor por defecto temporal
                    aptitudes = userDTO.competencias?.split(",")?.map { it.trim() },
                    celular = userDTO.celular
                )

                Result.success(conectaDTO.toDomain())
            } else {
                val errorMsg = "Error al obtener datos de usuario: ${response.code()}"
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
            Log.d("ConectaRepository", "Probando endpoint de usuario...")
            val response = userApiService.getUserByEmail("bhanccoco@unsa.edu.pe")
            Log.d("ConectaRepository", "Respuesta del servidor (test): ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                val userDTO = response.body()!!
                Log.d("ConectaRepository", "Test exitoso")

                val conectaDTO = ConectaDTO(
                    nombres = userDTO.nombres,
                    carrera = "Ingeniería de Sistemas",
                    palabras_clave = userDTO.competencias,
                    semestre = 8,
                    sobre_mi = "Estudiante de ${userDTO.nombres}",
                    aptitudes = userDTO.competencias?.split(",")?.map { it.trim() },
                    celular = userDTO.celular
                )

                Result.success(conectaDTO.toDomain())
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