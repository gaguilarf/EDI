package com.moly.edi.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moly.edi.domain.model.Project

@Dao
interface ProjectDao {

    @Query("SELECT * FROM ProjectEntity WHERE userId = :userId AND isSynced = 1")
    fun getSyncedProjectsByUserId(userId: Int): List<Project>

    @Query("SELECT * FROM ProjectEntity WHERE userId = :userId AND isSynced = 0 AND deletedLocally = 0")
    fun getUnsyncedProjectsByUserId(userId: Int): List<Project>

    @Query("UPDATE ProjectEntity SET isSynced = 1 WHERE id = :id")
    suspend fun markProjectAsSynced(id: Int)

    @Query("SELECT * FROM ProjectEntity WHERE userId = :userId AND modifiedLocally = 1 AND deletedLocally = 0")
    fun getModifiedProjects(userId: String): List<Project>

    @Query("SELECT * FROM ProjectEntity WHERE userId = :userId AND deletedLocally = 1")
    fun getDeletedProjects(userId: String): List<Project>

    @Insert
    fun insertProject(project: Project): Long

    @Update
    fun updateProject(project: Project): Int

    @Delete
    fun deleteProject(project: Project): Int
}
