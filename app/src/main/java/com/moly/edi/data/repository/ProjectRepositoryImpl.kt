package com.moly.edi.data.repository

import com.moly.edi.data.dataSource.local.dao.ProjectDao
import com.moly.edi.data.dataSource.remote.api.ProjectApiService
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.toDTO
import com.moly.edi.domain.repository.ProjectRepository
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val projectDao: ProjectDao,
    private val api: ProjectApiService
): ProjectRepository {

    override fun getProjectsByUserId(userId: Int): List<Project> {
        return projectDao.getSyncedProjectsByUserId(userId)
    }

    override fun insertProject(project: Project): Long {
        return projectDao.insertProject(project)
    }

    override fun updateProject(project: Project): Int {
        return projectDao.updateProject(project).toInt()
    }

    override fun deleteProject(project: Project): Int {
        return projectDao.deleteProject(project)
    }

    override suspend fun syncProjects(userId: String) {
        projectDao.getUnsyncedProjectsByUserId(userId.toInt()).forEach { project ->
            val response = api.createProject(project.toDTO())
            if (response.isSuccessful) {
                projectDao.updateProject(project.copy(isSynced = true))
            }
        }

        projectDao.getModifiedProjects(userId).forEach { project ->
            val response = api.updateProject(userId, project.id, project.toDTO())
            if (response.isSuccessful) {
                projectDao.updateProject(project.copy(modifiedLocally = false, isSynced = true))
            }
        }

        projectDao.getDeletedProjects(userId).forEach { project ->
            val response = api.deleteProject(project.id)
            if (response.isSuccessful) {
                projectDao.deleteProject(project)
            }
        }
    }

}