package com.moly.edi.presentation.noticias

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.domain.model.Noticia
import com.moly.edi.domain.repository.NoticiasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticiasViewModel @Inject constructor(
    private val noticiasRepository: NoticiasRepository
) : ViewModel() {

    private val _noticias = mutableStateOf<List<Noticia>>(emptyList())
    val noticias: State<List<Noticia>> = _noticias

    private val _isLoading = mutableStateOf(true)
    val isLoading: Boolean get() = _isLoading.value

    private var _error = mutableStateOf<String?>(null)
    val error: String? get() = _error.value

    private val _selectedNoticia = mutableStateOf<Noticia?>(null)
    val selectedNoticia: State<Noticia?> = _selectedNoticia

    init {
        fetchNoticias()
    }

    private fun fetchNoticias() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val listaNoticias = noticiasRepository.obtenerNoticiasUnsa()
                _noticias.value = listaNoticias

            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun obtenerNoticiasPorCategoria(categoria: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _noticias.value = if (categoria.isEmpty()) {
                    noticiasRepository.obtenerNoticiasUnsa()
                } else {
                    noticiasRepository.obtenerNoticiasPorCategoria(categoria)
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message ?: "desconocido"}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun seleccionarNoticia(noticia: Noticia) {
        _selectedNoticia.value = noticia
    }

    fun limpiarNoticiaSeleccionada() {
        _selectedNoticia.value = null
    }
}