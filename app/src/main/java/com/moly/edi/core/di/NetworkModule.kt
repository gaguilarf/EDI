package com.moly.edi.core.di


import com.moly.edi.data.dataSource.remote.api.AuthApiService
import com.moly.edi.data.dataSource.remote.api.ConfiguracionApiService
import com.moly.edi.data.dataSource.remote.api.NoticiasService
import com.moly.edi.data.dataSource.remote.api.ProjectApiService
import com.moly.edi.data.dataSource.remote.api.UserApiService
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
    fun provideConfiguracionApiService(retrofit: Retrofit): ConfiguracionApiService {
        return retrofit.create(ConfiguracionApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoticiasService(retrofit: Retrofit): NoticiasService {
        return retrofit.create(NoticiasService::class.java)
    }

    @Provides
    @Singleton
    fun provideProjectApiService(retrofit: Retrofit): ProjectApiService {
        return retrofit.create(ProjectApiService::class.java)
    }
}
