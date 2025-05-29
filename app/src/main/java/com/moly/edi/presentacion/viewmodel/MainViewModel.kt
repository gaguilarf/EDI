package com.moly.edi.presentacion.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.moly.edi.data.model.NoticiaUnsa
import com.moly.edi.data.repository.NoticiasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val noticiasRepository: NoticiasRepository
) : ViewModel() {

    private val _noticias = mutableStateOf<List<NoticiaUnsa>>(emptyList())
    val noticias: State<List<NoticiaUnsa>> = _noticias

    private val _noticiasImportantes = mutableStateOf<List<NoticiaUnsa>>(emptyList())
    val noticiasImportantes: State<List<NoticiaUnsa>> = _noticiasImportantes

    init {
        cargarNoticias()
    }

    fun cargarNoticias() {
        _noticias.value = noticiasRepository.obtenerNoticiasUnsa()
        _noticiasImportantes.value = _noticias.value.filter { it.esImportante }
    }

    fun obtenerNoticiasPorCategoria(categoria: String) {
        _noticias.value = noticiasRepository.obtenerNoticiasPorCategoria(categoria)
    }

    fun obtenerTodasLasNoticias() {
        cargarNoticias()
    }
}