package com.moly.edi.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.moly.edi.data.dataSource.local.entity.CategoriaEntity
import com.moly.edi.data.dataSource.local.entity.NoticiaConCategorias
import com.moly.edi.data.dataSource.local.entity.NoticiaEntity

@Dao
interface NoticiaDao {

    @Transaction
    @Query("SELECT * FROM noticias")
    suspend fun obtenerTodas(): List<NoticiaConCategorias>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarNoticia(noticia: NoticiaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCategorias(categorias: List<CategoriaEntity>)

    @Query("DELETE FROM categorias")
    suspend fun borrarCategorias()

    @Query("DELETE FROM noticias")
    suspend fun borrarNoticias()
}