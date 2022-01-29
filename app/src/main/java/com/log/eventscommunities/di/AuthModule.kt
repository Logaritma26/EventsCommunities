package com.log.eventscommunities.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    fun provideAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        ioDispatcher = ioDispatcher,
    )

    @Provides
    @Singleton
    fun provideSignInUseCase(
        repository: AuthRepository,
    ) = SignInUseCase(repository)

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        repository: AuthRepository,
    ) = RegisterUseCase(repository)

}