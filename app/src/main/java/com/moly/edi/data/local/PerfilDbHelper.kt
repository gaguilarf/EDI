package com.moly.edi.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PerfilDbHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USER)
        db.execSQL(SQL_CREATE_PROJECT)
        db.execSQL(SQL_CREATE_CHANGELOG)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS user")
        db.execSQL("DROP TABLE IF EXISTS project")
        db.execSQL("DROP TABLE IF EXISTS changelog")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "perfil.db"
        const val DATABASE_VERSION = 1

        // Tabla de usuario
        private const val SQL_CREATE_USER = """
            CREATE TABLE user (
                uuid TEXT PRIMARY KEY,
                nombre TEXT NOT NULL,
                correo TEXT NOT NULL,
                telefono TEXT,
                linkedin TEXT,
                github TEXT,
                instagram TEXT,
                tecnologias TEXT,
                last_modified_at INTEGER NOT NULL,
                is_deleted INTEGER DEFAULT 0
            )
        """

        // Tabla de proyecto
        private const val SQL_CREATE_PROJECT = """
            CREATE TABLE project (
                uuid TEXT PRIMARY KEY,
                user_uuid TEXT NOT NULL,
                titulo TEXT NOT NULL,
                descripcion TEXT,
                last_modified_at INTEGER NOT NULL,
                is_deleted INTEGER DEFAULT 0,
                FOREIGN KEY(user_uuid) REFERENCES user(uuid)
            )
        """

        // Tabla de log de cambios
        private const val SQL_CREATE_CHANGELOG = """
            CREATE TABLE changelog (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                table_name TEXT NOT NULL,
                record_uuid TEXT NOT NULL,
                operation_type TEXT NOT NULL,
                change_timestamp INTEGER NOT NULL
            )
        """
    }
}

