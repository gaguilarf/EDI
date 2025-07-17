package com.moly.edi.data.dataSource.remote.api

import com.moly.edi.data.model.ProjectDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProjectApiService {
    @GET("usuario/{userId}/proyectos")
    suspend fun getProjectsByUserId(@Path("userId") userId: String): Response<List<ProjectDTO>>

    @POST("usuario/{userId}/proyectos")
    suspend fun createProject(@Body project: ProjectDTO): Response<ProjectDTO>

    @POST("usuario/{userId}/proyectos/{projectId}")
    suspend fun updateProject(
        @Path("userId") userId: String,
        @Path("projectId") projectId: String,
        @Body project: ProjectDTO
    ): Response<ProjectDTO>

    @DELETE("usuario/{userId}/proyectos/{projectId}")
    suspend fun deleteProject(@Path("projectId") id: String): Response<Unit>
}