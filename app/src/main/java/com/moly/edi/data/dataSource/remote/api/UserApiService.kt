package com.moly.edi.data.dataSource.remote.api

import com.moly.edi.data.model.UserDTO
import com.moly.edi.data.model.ProjectDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @GET("usuario/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserDTO>

    @POST("usuario/{email}/proyectos")
    suspend fun addProjectToUser(
        @Path("email") email: String,
        @Body project: ProjectDTO
    ): Response<Unit>
}