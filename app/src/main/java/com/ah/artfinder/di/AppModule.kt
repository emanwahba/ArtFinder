package com.ah.artfinder.di

import com.ah.artfinder.data.remote.ArtApi
import com.ah.artfinder.data.repository.ArtRepository
import com.ah.artfinder.data.repository.ArtRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideArtRepository(api: ArtApi): ArtRepository = ArtRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideArtApi(retrofit: Retrofit): ArtApi = retrofit.create(ArtApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.rijksmuseum.nl/")
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()
}