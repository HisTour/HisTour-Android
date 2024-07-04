package com.startup.histour.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providesHeaderInterceptor(): Interceptor = Interceptor { chain ->
        with(chain) {
            val request = request().newBuilder()
                .build()
            proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesLoggerInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        headerInterceptor: Interceptor,
        loggerInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggerInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()
}
