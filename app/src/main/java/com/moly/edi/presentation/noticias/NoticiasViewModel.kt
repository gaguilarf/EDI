package com.moly.edi.presentation.noticias

import com.moly.edi.domain.model.Noticia

class NoticiasViewModel {
    val noticias = listOf(
        Noticia(
            author = "Author 1",
            date = "11/05/25",
            title = "Noticia 1",
            body = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                    "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                    "It has survived not only five centuries, but also the leap into electronic typesetting...",
            tags = listOf("evento", "trabajo"),
            likes = 10
        ),
        Noticia(
            author = "Author 2",
            date = "10/05/25",
            title = "Noticia 2",
            body = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                    "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                    "It has survived not only five centuries, but also the leap into electronic typesetting...",
            tags = listOf("beca", "educacion"),
            likes = 25
        )
    )
}