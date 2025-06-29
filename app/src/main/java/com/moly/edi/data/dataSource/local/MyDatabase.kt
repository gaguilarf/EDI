package com.moly.edi.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moly.edi.data.dataSource.local.dao.ProjectDao
import com.moly.edi.data.dataSource.local.dao.UserDao
import com.moly.edi.data.dataSource.local.dao.ConfiguracionDao
import com.moly.edi.data.model.Configuracion
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.User

@Database(entities = [
   // User::class,
  //  Project::class,
    Configuracion::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
   /// abstract fun userDao(): UserDao
   // abstract fun projectDao(): ProjectDao
    abstract fun configuracionDao(): ConfiguracionDao
}