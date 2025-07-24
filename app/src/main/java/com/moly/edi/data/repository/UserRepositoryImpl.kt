package com.moly.edi.data.repository

import android.content.Context
import android.util.Log
import com.moly.edi.data.dataSource.remote.api.UserApiService
import com.moly.edi.data.dataSource.local.PerfilLocalDataSource
import com.moly.edi.data.mapper.toDomain
import com.moly.edi.domain.model.User
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.toDTO
import com.moly.edi.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    //@Named("appContext") private val appContext: android.content.Context
    @ApplicationContext private val appContext: Context
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
                // Guardar en base de datos local
                try {
                    val localDataSource = PerfilLocalDataSource(appContext)
                    localDataSource.insertOrUpdateUser(user)
                    Log.d("UserRepository", "Perfil sincronizado")
                    val userFromDb = localDataSource.getUserByEmail(user.correo)
                    Log.d("UserRepository", "Tabla perfil: $userFromDb")
                } catch (e: Exception) {
                    Log.e("UserRepository", "Error guardando en SQLite: ${e.message}", e)
                }
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

    override suspend fun addProjectToUser(email: String, project: Project): Result<Unit> {
        return try {
            val projectDTO = project.toDTO() // Solo titulo y descripcion
            Log.d("UserRepository", "[addProjectToUser] Enviando al backend: $projectDTO")
            val response = apiService.addProjectToUser(email, projectDTO)
            Log.d("UserRepository", "[addProjectToUser] Respuesta backend: code=${response.code()} body=${response.body()} errorBody=${response.errorBody()?.string()}")
            if (response.isSuccessful) {
                Log.d("UserRepository", "[addProjectToUser] Proyecto agregado remotamente con éxito.")
                // Guardar también en la base local
                val localDataSource = PerfilLocalDataSource(appContext)
                val user = localDataSource.getUserByEmail(email)
                if (user != null) {
                    val updatedUser = user.copy(proyectos = user.proyectos + project)
                    localDataSource.insertOrUpdateUser(updatedUser)
                }
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error remoto: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "[addProjectToUser] Excepción: ${e.message}", e)
            Result.failure(e)
        }
    }
}
