package com.moly.edi.data.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// DTO para la petición de login
data class LoginRequest(
    val correo: String,
    val contraseña: String
)

data class LoginResponse(
    val message: String,
    val usuario: UserDTO?
)

interface AuthApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}

