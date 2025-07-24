package com.moly.edi.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "ConfiguracionEntity")
@TypeConverters(StringListConverter::class)
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
    //categorias_interes
    @ColumnInfo(name = "categorias_interes")
    val categoriasInteres: List<String>? = null,
    // Campos de sincronización
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,

    @ColumnInfo(name = "modified_locally")
    val modifiedLocally: Boolean = false,

    @ColumnInfo(name = "exists_in_server")
    val existsInServer: Boolean = false
)
class StringListConverter {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Gson().fromJson(value, object : TypeToken<List<String>>() {}.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}



