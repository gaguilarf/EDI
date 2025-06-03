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

    init {
        cargarNoticias()
    }

    fun cargarNoticias() {
        _noticias.value = noticiasRepository.obtenerNoticiasUnsa()
    }

    fun obtenerNoticiasPorCategoria(categoria: String) {
        if (categoria == "")
            _noticias.value = noticiasRepository.obtenerNoticiasUnsa()
        else
            _noticias.value = noticiasRepository.obtenerNoticiasPorCategoria(categoria)
    }

}