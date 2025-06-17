package com.moly.edi.core

import com.moly.edi.data.dataSource.api.entity.NoticiasService
import com.moly.edi.data.repositoryImpl.NoticiasRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.components.SingletonComponent
import com.moly.edi.data.repository.NoticiasRepository



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
        fun provideRepositoryNoticias(api: NoticiasService): NoticiasRepository =
            NoticiasRepositoryImpl(api)
    }
