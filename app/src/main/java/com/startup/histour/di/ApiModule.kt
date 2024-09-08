package com.startup.histour.di

import android.content.Context
import com.startup.histour.BuildConfig
import com.startup.histour.annotation.AuthHttpClient
import com.startup.histour.annotation.AuthRetrofit
import com.startup.histour.annotation.CommonHttpClient
import com.startup.histour.annotation.CommonRetrofit
import com.startup.histour.annotation.SSEHttpClient
import com.startup.histour.data.remote.api.AttractionApi
import com.startup.histour.data.remote.api.AuthApi
import com.startup.histour.data.remote.api.CharacterApi
import com.startup.histour.data.remote.api.HistoryApi
import com.startup.histour.data.remote.api.MemberApi
import com.startup.histour.data.remote.api.MissionApi
import com.startup.histour.data.remote.api.PlaceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providesHeaderInterceptor(@ApplicationContext context: Context): Interceptor = Interceptor { chain ->
        with(chain) {
            /* TODO 나중에 Auth 정보 삽입 */
            val request = request().newBuilder()
                .build()
            proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesLoggerInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @CommonHttpClient
    @Singleton
    fun providesCommonOkHttpClient(
        loggerInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggerInterceptor)
            .build()

    @Provides
    @AuthHttpClient
    @Singleton
    fun providesAuthOkHttpClient(
        headerInterceptor: Interceptor,
        loggerInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggerInterceptor)
            .build()

    @Provides
    @SSEHttpClient
    @Singleton
    fun providesSSEOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun providesConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @CommonRetrofit
    fun providesCommonRetrofit(
        @CommonHttpClient okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_DOMAIN)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    @AuthRetrofit
    fun providesAuthRetrofit(
        @AuthHttpClient okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_DOMAIN)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideMemberApi(@AuthRetrofit retrofit: Retrofit): MemberApi = retrofit.create(MemberApi::class.java)

    @Provides
    @Singleton
    fun provideAttractionApi(@AuthRetrofit retrofit: Retrofit): AttractionApi = retrofit.create(AttractionApi::class.java)

    @Provides
    @Singleton
    fun provideCharacterApi(@AuthRetrofit retrofit: Retrofit): CharacterApi = retrofit.create(CharacterApi::class.java)

    @Provides
    @Singleton
    fun provideHistoryApi(@AuthRetrofit retrofit: Retrofit): HistoryApi = retrofit.create(HistoryApi::class.java)

    @Provides
    @Singleton
    fun provideMissionApi(@AuthRetrofit retrofit: Retrofit): MissionApi = retrofit.create(MissionApi::class.java)

    @Provides
    @Singleton
    fun providePlaceApi(@AuthRetrofit retrofit: Retrofit): PlaceApi = retrofit.create(PlaceApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(@AuthRetrofit retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)
}
