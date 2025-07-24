package com.moly.edi.data.dataSource.remote.api

import com.moly.edi.data.model.ConectaDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ConectaService {
    @GET("usuario/{correo}/acerca")
    suspend fun getConectaByEmail(@Path("correo") correo: String): Response<ConectaDTO>
    
    @GET("usuario/test@test.com/acerca")
    suspend fun testEndpoint(): Response<ConectaDTO>
}