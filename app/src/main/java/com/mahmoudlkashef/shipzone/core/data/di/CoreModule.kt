package com.mahmoudlkashef.shipzone.core.data.di

import android.content.Context
import com.mahmoudlkashef.shipzone.core.data.local.DataStoreImpl
import com.mahmoudlkashef.shipzone.core.data.local.IDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {
    @Singleton
    @Provides
    fun providesUserDataStore(
        @ApplicationContext context: Context
    ): IDataStore {
        return DataStoreImpl(context)
    }
}