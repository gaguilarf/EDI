package com.moly.edi.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moly.edi.data.dataSource.local.dao.ProjectDao
import com.moly.edi.data.dataSource.local.dao.UserDao
import com.moly.edi.domain.model.Project
import com.moly.edi.domain.model.User

@Database(entities = [User::class, Project::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun projectDao(): ProjectDao
}