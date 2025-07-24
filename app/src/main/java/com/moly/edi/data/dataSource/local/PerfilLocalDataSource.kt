package com.moly.edi.data.dataSource.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.User
import java.util.UUID

class PerfilLocalDataSource(context: Context) {
    private val dbHelper = PerfilDbHelper(context)

    fun insertOrUpdateUser(user: User, now: Long = System.currentTimeMillis()) {
        val db = dbHelper.writableDatabase
        val uuid = user.id.toString()
        val values = ContentValues().apply {
            put("uuid", uuid)
            put("nombre", user.nombre)
            put("correo", user.correo)
            put("telefono", user.celular)
            put("linkedin", user.linkedin)
            put("github", user.github)
            put("instagram", user.instagram)
            put("tecnologias", user.competencias) // Usar tecnologias en lugar de competencias
            put("last_modified_at", now)
            put("is_deleted", 0)
        }
        val rows = db.update("user", values, "uuid=?", arrayOf(uuid))
        if (rows == 0) {
            db.insert("user", null, values)
            insertChangeLog("user", uuid, "INSERT", now)
        } else {
            insertChangeLog("user", uuid, "UPDATE", now)
        }
        // Proyectos
        user.proyectos.forEach { insertOrUpdateProject(it, uuid, now) }
    }

    fun insertOrUpdateProject(project: Project, userUuid: String, now: Long = System.currentTimeMillis()) {
        val db = dbHelper.writableDatabase
        val uuid = project.id.toString()
        val values = ContentValues().apply {
            put("uuid", uuid)
            put("user_uuid", userUuid)
            put("titulo", project.titulo)
            put("descripcion", project.descripcion)
            put("last_modified_at", now)
            put("is_deleted", 0)
        }
        val rows = db.update("project", values, "uuid=?", arrayOf(uuid))
        if (rows == 0) {
            db.insert("project", null, values)
            insertChangeLog("project", uuid, "INSERT", now)
        } else {
            insertChangeLog("project", uuid, "UPDATE", now)
        }
    }

    fun deleteUser(uuid: String, now: Long = System.currentTimeMillis()) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("is_deleted", 1)
            put("last_modified_at", now)
        }
        db.update("user", values, "uuid=?", arrayOf(uuid))
        insertChangeLog("user", uuid, "DELETE", now)
    }

    fun deleteProject(uuid: String, now: Long = System.currentTimeMillis()) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("is_deleted", 1)
            put("last_modified_at", now)
        }
        db.update("project", values, "uuid=?", arrayOf(uuid))
        insertChangeLog("project", uuid, "DELETE", now)
    }

    fun getUserByEmail(email: String): User? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "user", null, "correo=? AND is_deleted=0", arrayOf(email), null, null, null
        )
        val user = if (cursor.moveToFirst()) {
            val uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
            val telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
            val linkedin = cursor.getString(cursor.getColumnIndexOrThrow("linkedin"))
            val github = cursor.getString(cursor.getColumnIndexOrThrow("github"))
            val instagram = cursor.getString(cursor.getColumnIndexOrThrow("instagram"))
            val tecnologias = cursor.getString(cursor.getColumnIndexOrThrow("tecnologias")) // Cambiar competencias por tecnologias
            val proyectos = getProjectsByUserUuid(correo)
            User(
                id = correo, // Usar el correo como id para mantener la relación con los proyectos
                nombre = nombre,
                correo = correo,
                celular = telefono,
                linkedin = linkedin,
                instagram = instagram,
                github = github,
                competencias = tecnologias, // Mapear tecnologias a competencias
                proyectos = proyectos
            )
        } else null
        cursor.close()
        return user
    }

    fun getProjectsByUserUuid(userUuid: String): List<Project> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "project", null, "user_uuid=? AND is_deleted=0", arrayOf(userUuid), null, null, null
        )
        val projects = mutableListOf<Project>()
        while (cursor.moveToNext()) {
            val uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid"))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            projects.add(
                Project(
                    id = uuid,
                    userId = userUuid,
                    titulo = titulo,
                    descripcion = descripcion
                )
            )
        }
        cursor.close()
        return projects
    }

    fun insertChangeLog(table: String, recordUuid: String, op: String, timestamp: Long) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("table_name", table)
            put("record_uuid", recordUuid)
            put("operation_type", op)
            put("change_timestamp", timestamp)
        }
        db.insert("changelog", null, values)
    }

    fun getPendingChanges(since: Long): Cursor {
        val db = dbHelper.readableDatabase
        return db.query(
            "changelog", null, "change_timestamp>?", arrayOf(since.toString()), null, null, "change_timestamp ASC"
        )
    }
}