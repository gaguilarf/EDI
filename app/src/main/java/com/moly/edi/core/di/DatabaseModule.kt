package com.moly.edi.core.di

import android.content.Context
import androidx.room.Room
import com.moly.edi.data.dataSource.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.moly.edi.data.dataSource.local.dao.NoticiaDao


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mi_base.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideNoticiaDao(db: AppDatabase): NoticiaDao = db.noticiaDao()
}