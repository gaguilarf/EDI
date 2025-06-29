package com.moly.edi.data.dataSource.remote.api

import com.moly.edi.data.model.ConfiguracionDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ConfiguracionApiService {

    @GET("usuario/{correo_electronico}/configuracion")
    suspend fun getConfiguracionByUser(
        @Path("correo_electronico") correoElectronico: String
    ): Response<ConfiguracionDTO>

    @PUT("usuario/{correo_electronico}/configuracion")
    suspend fun updateConfiguracion(
        @Path("correo_electronico") correoElectronico: String,
        @Body configuracion: ConfiguracionDTO
    ): Response<ConfiguracionDTO>
}