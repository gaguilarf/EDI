package com.moly.edi.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moly.edi.data.dataSource.local.entity.NoticiaEntity
import com.moly.edi.data.dataSource.local.entity.CategoriaEntity
import com.moly.edi.data.dataSource.local.dao.NoticiaDao

@Database(
    entities = [NoticiaEntity::class, CategoriaEntity::class],
    version = 2
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noticiaDao(): NoticiaDao
}