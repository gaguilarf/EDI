// ===== MODELO DE DATOS (Room Entity) =====
package com.moly.edi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "ConfiguracionEntity")
data class Configuracion(
    @PrimaryKey
    @ColumnInfo(name = "id_usuario")
    val idUsuario: String,

    @ColumnInfo(name = "notificaciones_enabled")
    val notificacionesEnabled: Boolean,

    @ColumnInfo(name = "visibilidad_enabled")
    val visibilidadEnabled: Boolean,

    @ColumnInfo(name = "disponibilidad_enabled")
    val disponibilidadEnabled: Boolean,

    // Campos de sincronización
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,

    @ColumnInfo(name = "modified_locally")
    val modifiedLocally: Boolean = false,

    @ColumnInfo(name = "exists_in_server")
    val existsInServer: Boolean = false
)



