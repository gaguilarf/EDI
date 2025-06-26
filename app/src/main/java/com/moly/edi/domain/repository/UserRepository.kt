package com.moly.edi.domain.repository

import com.moly.edi.domain.model.User

interface UserRepository {
    suspend fun getUserByEmail(email: String): Result<User>
}