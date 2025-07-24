package com.moly.edi.data.dataSource.remote.api

import com.moly.edi.data.model.NoticiaDTO
import retrofit2.http.GET
interface NoticiasService {
    @GET("/noticias")
    suspend fun getNoticias(): List<NoticiaDTO>
}