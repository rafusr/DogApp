package com.rezzavinola.dogapplication.di

import com.rezzavinola.dogapplication.data.remote.ApiClient
import com.rezzavinola.dogapplication.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiClient.instance
    }

}