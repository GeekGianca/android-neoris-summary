package com.example.neoris.network.di

import com.example.neoris.network.client.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkClientModule {
    @Singleton
    @Provides
    fun provideNetworkClient(retrofit: Retrofit): NetworkClient = retrofit.create(NetworkClient::class.java)
}