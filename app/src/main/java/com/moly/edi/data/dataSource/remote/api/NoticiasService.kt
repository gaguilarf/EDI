package com.moly.edi.data.dataSource.remote.api

import com.moly.edi.data.model.NoticiaDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NoticiasService {
    @GET("/noticias")
    suspend fun getNoticias(): List<NoticiaDTO>
    
    @POST("noticia/{noticia_id}/reaccion")
    suspend fun modificarReaccion(
        @Path("noticia_id") noticiaId: String,
        @Body request: ReaccionRequest
    ): Response<ReaccionResponse>
}

data class ReaccionRequest(
    val accion: String // "agregar" o "quitar"
)

data class ReaccionResponse(
    val message: String,
    val reacciones: Int
)