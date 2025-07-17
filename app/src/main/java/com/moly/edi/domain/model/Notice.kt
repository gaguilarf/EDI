package com.moly.edi.domain.model

data class Notice(
    val author: String,
    val date: String,
    val title: String,
    val body: String,
    val tags: List<String>,
    val likes: Number,
)
