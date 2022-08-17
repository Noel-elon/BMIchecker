package com.example.noelnwaelugo.di

import com.example.noelnwaelugo.datasource.BMIRepoImpl
import com.example.noelnwaelugo.datasource.BMIRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @Singleton
    abstract fun provideRepo(repoImpl: BMIRepoImpl): BMIRepository
}