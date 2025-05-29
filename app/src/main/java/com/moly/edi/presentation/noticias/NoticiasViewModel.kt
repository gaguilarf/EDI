package com.moly.edi.presentation.noticias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moly.edi.domain.model.Noticia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoticiasViewModel : ViewModel() {

    private val _noticias = MutableStateFlow<List<Noticia>>(emptyList())
    val noticias: StateFlow<List<Noticia>> = _noticias

    init {
        loadNoticias()
    }

    private fun loadNoticias() {
        viewModelScope.launch {
            //aqui ira la llamada futura a la api
            _noticias.value = listOf(
                Noticia(
                    author = "Author 1",
                    date = "11/05/25",
                    title = "Noticia 1",
                    body = "Lorem Ipsum is simply dummy text...",
                    tags = listOf("evento", "trabajo"),
                    likes = 10
                ),
                Noticia(
                    author = "Author 2",
                    date = "10/05/25",
                    title = "Noticia 2",
                    body = "Lorem Ipsum is simply dummy text...",
                    tags = listOf("beca", "educacion"),
                    likes = 25
                )
            )
        }
    }
}