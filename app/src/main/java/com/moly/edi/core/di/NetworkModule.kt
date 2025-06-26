package com.moly.edi.core.di

import com.moly.edi.data.dataSource.remote.api.NoticiasService
import com.moly.edi.data.dataSource.remote.api.AuthApiService
import com.moly.edi.data.dataSource.remote.api.ProjectApiService
import com.moly.edi.data.dataSource.remote.api.UserApiService
import com.moly.edi.domain.repository.NoticiasRepository
import com.moly.edi.data.repository.NoticiasRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://edi-backend-vgou.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): NoticiasService =
        retrofit.create(NoticiasService::class.java)

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideProjectApiService(retrofit: Retrofit): ProjectApiService =
        retrofit.create(ProjectApiService::class.java)

    @Provides
    @Singleton
    fun provideRepositoryNoticias(api: NoticiasService): NoticiasRepository =
        NoticiasRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)
}