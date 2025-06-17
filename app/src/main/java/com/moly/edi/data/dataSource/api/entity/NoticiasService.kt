package com.moly.edi.data.dataSource.api.entity

import retrofit2.http.GET
interface NoticiasService {
    @GET("/noticias")
    suspend fun getNoticias(): List<NoticiaResponse>
}