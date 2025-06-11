package com.moly.edi.data.repository

import com.moly.edi.data.model.NoticiaUnsa
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoticiasRepositoryImpl @Inject constructor() : NoticiasRepository {

    // Simulamos datos de noticias de la UNSA
    private val noticiasUnsa = listOf(
        NoticiaUnsa(
            id = 1,
            titulo = "Convocatoria de Becas 2025 - UNSA",
            contenido = "La Universidad Nacional de San Agustín convoca a estudiantes de pregrado para postular a becas de excelencia académica...",
            fecha = "29 Mayo 2025",
            categoria = "Becas",
            esImportante = true
        ),
        NoticiaUnsa(
            id = 2,
            titulo = "Inicio de Matriculas - Semestre 2025-I",
            contenido = "Se informa a toda la comunidad universitaria que el proceso de matrículas para el semestre académico 2025-I...",
            fecha = "28 Mayo 2025",
            categoria = "Académico",
            esImportante = true
        ),
        NoticiaUnsa(
            id = 3,
            titulo = "Evento Cultural: Semana de Ingeniería de Sistemas",
            contenido = "La Escuela Profesional de Ingeniería de Sistemas invita a participar en la Semana Cultural...",
            fecha = "27 Mayo 2025",
            categoria = "Eventos",
            esImportante = false
        ),
        NoticiaUnsa(
            id = 4,
            titulo = "Comunicado: Suspensión de Clases",
            contenido = "Por motivos de mantenimiento de la infraestructura universitaria, se suspenden las clases presenciales...",
            fecha = "26 Mayo 2025",
            categoria = "Comunicados",
            esImportante = true
        )
    )

    override fun obtenerNoticiasUnsa(): List<NoticiaUnsa> {
        return noticiasUnsa
    }

    override fun obtenerNoticiasPorCategoria(categoria: String): List<NoticiaUnsa> {
        return noticiasUnsa.filter { it.categoria == categoria }
    }
}

