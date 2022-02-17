package com.log.eventscommunities.di

import com.google.firebase.firestore.FirebaseFirestore
import com.log.eventscommunities.data.manager.FirebaseFunctionManager
import com.log.eventscommunities.data.repository.EventRepositoryImpl
import com.log.eventscommunities.domain.repository.EventRepository
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
        functionManager: FirebaseFunctionManager,
        fireStore: FirebaseFirestore,
    ): EventRepository = EventRepositoryImpl(
        ioDispatcher = ioDispatcher,
        functionManager = functionManager,
        fireStore = fireStore,
    )

}