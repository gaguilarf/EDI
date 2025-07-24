package com.moly.edi.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moly.edi.data.dataSource.local.dao.ConfiguracionDao
import com.moly.edi.data.dataSource.local.dao.ProjectDao
//import com.moly.edi.data.dataSource.local.dao.UserDao
import com.moly.edi.domain.model.Configuracion
import com.moly.edi.domain.model.User
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.StringListConverter

@Database(entities = [
    //User::class,
    Project::class,
    Configuracion::class],
    version=2,
     exportSchema= false)

@TypeConverters(StringListConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun configuracionDao(): ConfiguracionDao
    //abstract fun userDao(): UserDao
    abstract fun projectDao(): ProjectDao
}