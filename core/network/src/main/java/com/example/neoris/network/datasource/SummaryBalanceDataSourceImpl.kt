package com.example.neoris.network.datasource

import android.util.Log
import com.example.neoris.core.model.BalanceSummaryModel
import com.example.neoris.network.client.NetworkClient
import com.example.neoris.network.errorCatch
import com.example.neoris.shared.network.Dispatcher
import com.example.neoris.shared.network.ExampleDispatchers.IO
import com.example.neoris.shared.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SummaryBalanceDataSourceImpl @Inject constructor(
    @param:Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val client: NetworkClient
) : ISummaryBalanceDataSource {
    override suspend fun getBalanceSummary(): Result<BalanceSummaryModel> =
        withContext(ioDispatcher) {
            try {
                val result = client.getFinancialSummary()
                if (result.record.transactions.isEmpty() || result.record.balance == 0.0) {
                    Result.Empty
                } else {
                    Result.Success(result)
                }
            } catch (e: Exception) {
                e.errorCatch()
            }
        }


}