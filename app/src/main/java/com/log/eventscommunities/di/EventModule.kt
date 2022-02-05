package com.log.eventscommunities.di

import com.log.eventscommunities.data.manager.FirebaseFunctionManager
import com.log.eventscommunities.data.repository.HomeRepositoryImpl
import com.log.eventscommunities.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventModule {

    @Provides
    @Singleton
    fun provideHomeRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        functionManager: FirebaseFunctionManager
    ): HomeRepository = HomeRepositoryImpl(
        ioDispatcher = ioDispatcher,
        functionManager = functionManager,
    )

}