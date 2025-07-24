package com.moly.edi.data.mapper

import com.moly.edi.data.model.NoticiaDTO
import com.moly.edi.data.model.ProjectDTO
import com.moly.edi.data.model.ConectaDTO
import com.moly.edi.domain.model.User
import com.moly.edi.data.model.UserDTO
import com.moly.edi.domain.model.Noticia
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.Conecta

fun ProjectDTO.toDomain(): Project {
    return Project(
        id = this.id ?: "0",
        userId = "",
        titulo = this.titulo ?: "",
        descripcion = this.descripcion ?: ""
    )
}


fun Project.toDTO(): ProjectDTO {
    return ProjectDTO(
        id = this.id,
        titulo = this.titulo,
        descripcion = this.descripcion
    )
}

fun NoticiaDTO.toDomain(): Noticia {
    return Noticia(
        autor = this.autor,
        reacciones = this.reacciones,
        categoria = this.categoria,
        descripcion_larga = this.descripcion_larga,
        descripcion_card = this.descripcion_card,
        fecha = this.fecha,
        id = this.id,
        titulo = this.titulo,
        esImportante = false // Valor por defecto, ya que DTO no lo incluye
    )
}

fun Noticia.toDTO(): NoticiaDTO {
    return NoticiaDTO(
        autor = this.autor,
        reacciones = this.reacciones,
        categoria = this.categoria,
        descripcion_larga = this.descripcion_larga,
        descripcion_card = this.descripcion_card,
        fecha = this.fecha,
        id = this.id,
        titulo = this.titulo
    )
}

fun UserDTO.toDomain(): User {
    return User(
        id = this.id,
        nombre = this.nombres,
        correo = this.correo,
        celular = this.celular,
        linkedin = this.redes?.linkedin,
        instagram = this.redes?.instagram,
        github = this.redes?.github,
        competencias = this.competencias,
        proyectos = this.proyectos?.map { it.toDomain() } ?: emptyList()
    )
}

fun ConectaDTO.toDomain(): Conecta {
    return Conecta(
        id = "", // El id no se usa en la respuesta actual
        carrera = this.carrera,
        competencias = this.competencias,
        nombres = this.nombres,
        semestre = this.semestre,
        sobre_mi = this.sobre_mi,
        celular = this.celular
    )
}

fun Conecta.toDTO(): ConectaDTO {
    return ConectaDTO(
        nombres = this.nombres ?: "",
        carrera = this.carrera,
        palabras_clave = this.competencias,
        semestre = this.semestre,
        sobre_mi = this.sobre_mi,
        celular = this.celular
    )
}