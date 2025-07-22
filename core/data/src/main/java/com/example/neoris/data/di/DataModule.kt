package com.example.neoris.data.di

import com.example.neoris.data.repository.ISummaryBalanceRepository
import com.example.neoris.data.repository.SummaryBalanceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindSummaryRepository(
        summaryRepository: SummaryBalanceRepositoryImpl,
    ): ISummaryBalanceRepository
}