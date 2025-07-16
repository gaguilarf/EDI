package com.moly.edi.core.di

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

        /*@Provides
        @Singleton
        fun providePlantApi(retrofit: Retrofit): PlantApi =
            retrofit.create(PlantApi::class.java)

        @Provides
        @Singleton
        fun providePlantRepository(api: PlantApi): PlantRepository =
            PlantRepository(api)*/
}
