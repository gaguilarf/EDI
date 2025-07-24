package com.moly.edi.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moly.edi.data.model.Configuracion

@Dao
interface ConfiguracionDao {

    @Query("SELECT * FROM ConfiguracionEntity WHERE id_usuario = :userId")
    suspend fun getConfiguracionByUserId(userId: String): Configuracion?

    @Query("SELECT * FROM ConfiguracionEntity WHERE id_usuario = :userId AND is_synced = 1")
    suspend fun getSyncedConfiguracionByUserId(userId: String): Configuracion?

    @Query("SELECT * FROM ConfiguracionEntity WHERE id_usuario = :userId AND is_synced = 0")
    suspend fun getUnsyncedConfiguracionByUserId(userId: String): Configuracion?

    @Query("SELECT * FROM ConfiguracionEntity WHERE id_usuario = :userId AND modified_locally = 1")
    suspend fun getModifiedConfiguracion(userId: String): Configuracion?

    @Query("UPDATE ConfiguracionEntity SET is_synced = 1 WHERE id_usuario = :userId")
    suspend fun markConfiguracionAsSynced(userId: String): Int

    @Query("UPDATE ConfiguracionEntity SET modified_locally = 0 WHERE id_usuario = :userId")
    suspend fun markConfiguracionAsNotModified(userId: String): Int

    @Query("UPDATE ConfiguracionEntity SET exists_in_server = 1 WHERE id_usuario = :userId")
    suspend fun markConfiguracionAsExistsInServer(userId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateConfiguracion(configuracion: Configuracion): Long

    @Update
    suspend fun updateConfiguracion(configuracion: Configuracion): Int

    @Query("DELETE FROM ConfiguracionEntity WHERE id_usuario = :userId")
    suspend fun deleteConfiguracionByUserId(userId: String): Int

    @Query("SELECT COUNT(*) FROM ConfiguracionEntity WHERE id_usuario = :userId")
    suspend fun configurationExists(userId: String): Int

    @Query("SELECT * FROM ConfiguracionEntity WHERE modified_locally = 1")
    suspend fun getAllModifiedConfigurations(): List<Configuracion>

    @Query("SELECT * FROM ConfiguracionEntity WHERE is_synced = 0")
    suspend fun getAllUnsyncedConfigurations(): List<Configuracion>

    // ===== NUEVOS MÉTODOS ÚTILES =====

    @Query("SELECT COUNT(*) FROM ConfiguracionEntity WHERE modified_locally = 1")
    suspend fun countPendingChanges(): Int

    @Query("UPDATE ConfiguracionEntity SET is_synced = 1, modified_locally = 0, exists_in_server = 1 WHERE id_usuario = :userId")
    suspend fun markAsFullySynced(userId: String): Int

    @Query("SELECT * FROM ConfiguracionEntity WHERE exists_in_server = 0")
    suspend fun getConfigurationsNotInServer(): List<Configuracion>
}