package com.moly.edi.core.di
/*
import android.content.Context
//import com.moly.edi.data.repository.ProjectRepositoryImpl
import com.moly.edi.domain.repository.UserRepository
//import com.moly.edi.data.repository.UserRepositoryImpl
import com.moly.edi.domain.repository.ProjectRepository*/

import com.moly.edi.data.repository.ConfiguracionRepositoryImpl
import com.moly.edi.domain.repository.ConfiguracionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindConfiguracionRepository(
        configuracionRepositoryImpl: ConfiguracionRepositoryImpl
    ): ConfiguracionRepository

/*@Binds
@Singleton
abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

@Binds
@Singleton
abstract fun bindProjectRepository(impl: ProjectRepositoryImpl): ProjectRepository

companion object {
    @Provides
    @Singleton
    @Named("appContext")
    fun provideAppContext(
        @ApplicationContext context: Context
    ): Context = context
}*/
}
