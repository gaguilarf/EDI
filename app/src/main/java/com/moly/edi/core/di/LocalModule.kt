package com.moly.edi.core.di

import android.content.Context
import androidx.room.Room
import com.moly.edi.data.dataSource.local.MyDatabase
import com.moly.edi.data.dataSource.local.dao.ProjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(
            context,
            MyDatabase::class.java,
            "my_database"
        ).build()
    }

    @Provides
    fun provideProjectDao(database: MyDatabase): ProjectDao {
        return database.projectDao()
    }
}