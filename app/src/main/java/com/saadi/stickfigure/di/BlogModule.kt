package com.saadi.stickfigure.di

import com.saadi.stickfigure.feature_auth.data.data_source.AuthService
import com.saadi.stickfigure.feature_auth.data.repository.AuthRepositoryImp
import com.saadi.stickfigure.feature_auth.domain.repository.AuthRepository
import com.saadi.stickfigure.feature_auth.domain.usecase.AuthUseCases
import com.saadi.stickfigure.feature_auth.domain.usecase.ForgotPassword
import com.saadi.stickfigure.feature_auth.domain.usecase.SignIn
import com.saadi.stickfigure.feature_auth.domain.usecase.SignUp
import com.saadi.stickfigure.feature_blog.data.repository.BlogRepositoryImp
import com.saadi.stickfigure.feature_blog.domain.repository.BlogRepository
import com.saadi.stickfigure.feature_blog.domain.usecase.BlogUseCases
import com.saadi.stickfigure.feature_blog.domain.usecase.GetBlogs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object BlogModule {

    @Singleton
    @Provides
    fun provideBlogRepository(service: AuthService): BlogRepository {
        return BlogRepositoryImp()
    }

    @Provides
    @Singleton
    fun providesBlogUseCases(repository: BlogRepository): BlogUseCases {
        return BlogUseCases(
            blogs = GetBlogs(repository)
        )
    }

}