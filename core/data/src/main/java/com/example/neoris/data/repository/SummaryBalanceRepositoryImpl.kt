package com.example.neoris.data.repository

import com.example.neoris.core.model.BalanceSummaryModel
import com.example.neoris.network.datasource.ISummaryBalanceDataSource
import com.example.neoris.shared.network.Dispatcher
import com.example.neoris.shared.network.ExampleDispatchers.IO
import com.example.neoris.shared.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SummaryBalanceRepositoryImpl @Inject constructor(
    private val summaryDataSource: ISummaryBalanceDataSource,
    @param:Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : ISummaryBalanceRepository {
    override fun summaryBalance(): Flow<Result<BalanceSummaryModel>> = flow {
        emit(summaryDataSource.getBalanceSummary())
    }.flowOn(ioDispatcher)
}