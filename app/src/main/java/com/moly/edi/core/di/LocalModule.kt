package com.moly.edi.core.di
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.moly.edi.data.dataSource.local.dao.ConfiguracionDao
import com.moly.edi.data.dataSource.local.dao.ProjectDao
import com.moly.edi.data.dataSource.local.MyDatabase
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
        )
            .addMigrations(MIGRATION_1_2)  // AGREGAR MIGRACIÓN
            .build()
    }
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ConfiguracionEntity ADD COLUMN categorias_interes TEXT DEFAULT '[]'")
        }
    }


    @Provides
    @Singleton
    fun provideProjectDao(database: MyDatabase): ProjectDao {
        return database.projectDao()
    }


    @Provides
    @Singleton
    fun provideConfiguracionDao(database: MyDatabase): ConfiguracionDao {
        return database.configuracionDao()
    }
}