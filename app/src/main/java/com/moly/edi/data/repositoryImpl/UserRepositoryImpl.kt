package com.moly.edi.data.repositoryImpl

import android.util.Log
import com.moly.edi.data.model.UserApiService
import com.moly.edi.data.model.toDomain
import com.moly.edi.domain.model.User
import com.moly.edi.data.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {

    override suspend fun getUserByEmail(email: String): Result<User> {
        return try {
            Log.d("UserRepository", "Fetching user with email: $email")
            val response = apiService.getUserByEmail(email)
            Log.d("UserRepository", "Response code: ${response.code()}")
            
            if (response.isSuccessful && response.body() != null) {
                Log.d("UserRepository", "User DTO fetched successfully: ${response.body()}")
                val user = response.body()!!.toDomain()
                Log.d("UserRepository", "User domain model: $user")
                Result.success(user)
            } else {
                val errorMessage = "Failed to fetch user: ${response.errorBody()?.string()}"
                Log.e("UserRepository", errorMessage)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception fetching user: ${e.message}", e)
            Result.failure(e)
        }
    }
}
