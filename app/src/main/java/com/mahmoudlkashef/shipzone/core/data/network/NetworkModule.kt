package com.mahmoudlkashef.shipzone.core.data.network

import com.mahmoudlkashef.shipzone.core.data.local.DataStoreImpl
import com.mahmoudlkashef.shipzone.core.data.local.IDataStore
import com.mahmoudlkashef.shipzone.searchzone.data.repository.SearchZoneRepositoryImpl
import com.mahmoudlkashef.shipzone.searchzone.domain.repository.SearchZoneRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAppInterceptor(dataStore: IDataStore): Interceptor = AppInterceptor(dataStore)

    @Provides
    @Singleton
    fun provideOkHttpClient(appInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(appInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://stg-app.bosta.co/api/v2/")
            .addConverterFactory(createGsonConverter())
            .client(client)
            .build()
}
