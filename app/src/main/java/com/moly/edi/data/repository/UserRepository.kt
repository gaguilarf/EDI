package com.moly.edi.data.repository

import com.moly.edi.domain.model.User

interface UserRepository {
    suspend fun getUserByEmail(email: String): Result<User>
}