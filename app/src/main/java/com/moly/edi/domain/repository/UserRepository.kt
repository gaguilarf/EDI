package com.moly.edi.domain.repository

import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.User

interface UserRepository {
    suspend fun getUserByEmail(email: String): Result<User>
    suspend fun addProjectToUser(email: String, project: Project): Result<Unit>
}