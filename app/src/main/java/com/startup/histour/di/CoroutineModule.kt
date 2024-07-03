package com.startup.histour.di
import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.annotation.MainImmediate
import com.startup.histour.annotation.UnConfined
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    @IO
    fun providesIOCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Main
    fun providesMainCoroutineContext(): CoroutineContext = Dispatchers.Main

    @Provides
    @MainImmediate
    fun providesMainImmediateCoroutineContext(): CoroutineContext = Dispatchers.Main.immediate

    @Provides
    @UnConfined
    fun providesUnconfinedCoroutineContext(): CoroutineContext = Dispatchers.Unconfined

    @Provides
    @Singleton
    @IOScope
    fun providesIOCoroutineScope(
        @IO ioDispatcher: CoroutineContext,
    ): CoroutineScope = CoroutineScope(ioDispatcher)
}
