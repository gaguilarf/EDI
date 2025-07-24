package com.moly.edi.presentation.noticias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.domain.model.Noticia
import com.moly.edi.domain.repository.NoticiasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticiaDetalleViewModel @Inject constructor(
    private val noticiasRepository: NoticiasRepository
) : ViewModel() {

    private val _noticia = MutableStateFlow<Noticia?>(null)
    val noticia: StateFlow<Noticia?> = _noticia.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cargarNoticia(noticiaId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                // Obtener todas las noticias y buscar la que coincida con el ID
                val todasLasNoticias = noticiasRepository.obtenerNoticiasUnsa()
                val noticiaEncontrada = todasLasNoticias.find { it.id == noticiaId }
                
                if (noticiaEncontrada != null) {
                    _noticia.value = noticiaEncontrada
                } else {
                    // Si no se encuentra, crear una noticia de ejemplo
                    _noticia.value = Noticia(
                        id = noticiaId,
                        titulo = "Noticia $noticiaId",
                        autor = "Autor de la noticia",
                        fecha = "2024-01-01",
                        categoria = "General",
                        descripcion_larga = "Esta es la descripción larga de la noticia $noticiaId. Aquí puedes ver todos los detalles de la noticia, incluyendo información adicional que no se muestra en la vista de tarjeta.",
                        descripcion_card = "Descripción corta de la noticia",
                        reacciones = 0,
                        esImportante = false
                    )
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar la noticia: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun modificarReaccion(accion: String, onResult: (Boolean, Int) -> Unit) {
        _noticia.value?.let { noticia ->
            viewModelScope.launch {
                val result = noticiasRepository.modificarReaccion(noticia.id, accion)
                result.fold(
                    onSuccess = { nuevasReacciones ->
                        // Actualizar la noticia con las nuevas reacciones
                        _noticia.value = noticia.copy(reacciones = nuevasReacciones)
                        onResult(true, nuevasReacciones)
                    },
                    onFailure = { exception ->
                        // En caso de error, mantener el estado anterior
                        onResult(false, noticia.reacciones)
                    }
                )
            }
        }
    }
} 