package com.example.neoris.network.di

import com.example.neoris.network.datasource.ISummaryBalanceDataSource
import com.example.neoris.network.datasource.SummaryBalanceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSource {
    @Binds
    internal abstract fun bindSummaryDataSource(
        summaryDataSource: SummaryBalanceDataSourceImpl
    ): ISummaryBalanceDataSource
}