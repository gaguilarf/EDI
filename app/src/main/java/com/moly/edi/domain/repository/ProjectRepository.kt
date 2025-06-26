package com.moly.edi.domain.repository

import com.moly.edi.domain.model.Project

interface ProjectRepository {
    fun getProjectsByUserId(userId: Int): List<Project>

    fun insertProject(project: Project): Long

    fun updateProject(project: Project): Int

    fun deleteProject(project: Project): Int

    suspend fun syncProjects(userId: String)
}