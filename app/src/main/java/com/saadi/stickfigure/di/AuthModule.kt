package com.saadi.stickfigure.di

import com.saadi.stickfigure.feature_auth.data.data_source.AuthService
import com.saadi.stickfigure.feature_auth.data.repository.AuthRepositoryImp
import com.saadi.stickfigure.feature_auth.domain.repository.AuthRepository
import com.saadi.stickfigure.feature_auth.domain.usecase.AuthUseCases
import com.saadi.stickfigure.feature_auth.domain.usecase.SignIn
import com.saadi.stickfigure.feature_auth.domain.usecase.SignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(service: AuthService): AuthRepository {
        return AuthRepositoryImp(service)
    }

    @Provides
    @Singleton
    fun providesAuthUseCases(repository: AuthRepository): AuthUseCases{
        return AuthUseCases(
           signIn = SignIn(repository = repository),
           signUp = SignUp(repository = repository)
        )
    }

}