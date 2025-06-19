package com.moly.edi.data.repositoryImpl

import com.moly.edi.data.model.UserApiService
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
            val response = apiService.getUserByEmail(email)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch user: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
