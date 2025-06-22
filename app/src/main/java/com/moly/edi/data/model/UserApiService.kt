package com.moly.edi.data.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("usuario/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserDTO>
}