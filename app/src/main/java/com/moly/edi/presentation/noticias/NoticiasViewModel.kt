package com.moly.edi.presentation.noticias

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.data.model.NoticiaUnsa
import com.moly.edi.data.repository.NoticiasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticiasViewModel @Inject constructor(
    private val noticiasRepository: NoticiasRepository
) : ViewModel() {

    private val _noticias = mutableStateOf<List<NoticiaUnsa>>(emptyList())
    val noticias: State<List<NoticiaUnsa>> = _noticias

    private val _isLoading = mutableStateOf(true)
    val isLoading: Boolean get() = _isLoading.value

    private var _error = mutableStateOf<String?>(null)
    val error: String? get() = _error.value

    init {
        fetchNoticias()
    }

    private fun fetchNoticias() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                noticiasRepository.sincronizarNoticias()
                //val listaNoticias = noticiasRepository.obtenerNoticiasUnsa()
                val listaNoticias = noticiasRepository.obtenerNoticiasLocal()
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

}