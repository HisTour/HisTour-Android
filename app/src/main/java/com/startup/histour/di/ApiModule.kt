package com.startup.histour.di

import android.util.Log
import com.startup.histour.annotation.CommonHttpClient
import com.startup.histour.annotation.SSEHttpClient
import com.startup.histour.data.util.EventSourceManager
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
            val response = proceed(request)
            Log.e("LMH", "RESPONSE ${response.body?.toString()}")
            Log.e("LMH", "RESPONSE ${response.message}")
            Log.e("LMH", "RESPONSE ${response.body?.string().orEmpty()}")
            response
        }
    }

    @Provides
    @Singleton
    fun providesLoggerInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @CommonHttpClient
    @Singleton
    fun providesCommonOkHttpClient(
        headerInterceptor: Interceptor,
        loggerInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggerInterceptor)
            .build()

    @Provides
    @SSEHttpClient
    @Singleton
    fun providesSSEOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    @Provides
    @Singleton
    fun providesConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

}
