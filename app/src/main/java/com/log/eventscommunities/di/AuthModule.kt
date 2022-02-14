package com.log.eventscommunities.di

import com.google.firebase.auth.FirebaseAuth
import com.log.eventscommunities.data.repository.AuthRepositoryImpl
import com.log.eventscommunities.domain.repository.AuthRepository
import com.log.eventscommunities.domain.use_case.auth.RegisterUseCase
import com.log.eventscommunities.domain.use_case.auth.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        ioDispatcher = ioDispatcher,
    )

}