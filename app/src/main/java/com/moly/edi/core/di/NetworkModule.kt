package com.moly.edi.core.di

import com.moly.edi.data.dataSource.api.entity.NoticiasService
import com.moly.edi.data.model.UserApiService
import com.moly.edi.data.repository.NoticiasRepository
import com.moly.edi.data.repositoryImpl.NoticiasRepositoryImpl
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
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://edi-backend-ww44.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

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
    fun provideRepositoryNoticias(api: NoticiasService): NoticiasRepository =
        NoticiasRepositoryImpl(api)
}