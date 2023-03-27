package com.saadi.stickfigure.di

import com.saadi.stickfigure.utils.NetworkConnectivityService
import com.saadi.stickfigure.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
            /*here we can add headers for all api call by adding request.addHeader()*/
            request.addHeader("Accept","application/json")
            request.addHeader("Device-id","alsdkfjasdfasdfasdfasdfas")
            val actualRequest = request.build()
            if (!NetworkConnectivityService.instance.isOnline()) {
                throw IOException("No internet connection")
            } else {
                it.proceed(actualRequest)
            }
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(50, java.util.concurrent.TimeUnit.SECONDS)

        return httpBuilder
            .protocols(mutableListOf(Protocol.HTTP_1_1))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()
    }

}