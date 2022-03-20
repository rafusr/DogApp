package com.rezzavinola.dogapplication.di

import android.app.Application
import com.rezzavinola.dogapplication.data.local.DogsDao
import com.rezzavinola.dogapplication.data.local.DogsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): DogsDatabase {
        return DogsDatabase.instance(application)
    }

    @Provides
    @Singleton
    fun provideCharactersDao(dogsDatabase: DogsDatabase): DogsDao {
        return dogsDatabase.dogs()
    }

}