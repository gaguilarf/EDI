package com.moly.edi.presentation.perfil

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProyectosViewModel @Inject constructor(
    private val repository: ProjectRepository
) : ViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    var currentProyecto by mutableStateOf<Project?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun abrirDialogoEditar(proyecto: Project?) {
        currentProyecto = proyecto
        showDialog = true
    }

    fun cerrarDialogo() {
        showDialog = false
        currentProyecto = null
        errorMessage = null
    }

    fun guardarProyecto(proyecto: Project) {
        viewModelScope.launch {
            try {
                if (proyecto.titulo.isBlank()) {
                    errorMessage = "El título no puede estar vacío"
                    return@launch
                }

                if (currentProyecto != null) {
                    repository.updateProject(
                        proyecto.copy(
                            modifiedLocally = true,
                            isSynced = false,
                            lastModified = System.currentTimeMillis()
                        )
                    )
                } else {
                    repository.insertProject(
                        proyecto.copy(
                            isSynced = false,
                            deletedLocally = false,
                            modifiedLocally = false,
                            createdAt = System.currentTimeMillis(),
                            lastModified = System.currentTimeMillis()
                        )
                    )
                }

                cerrarDialogo()

            } catch (e: Exception) {
                errorMessage = "Error al guardar el proyecto: ${e.message}"
            }
        }
    }
}
