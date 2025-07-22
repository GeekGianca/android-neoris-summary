package com.example.neoris.network.datasource

import com.example.neoris.core.model.BalanceSummaryModel
import com.example.neoris.shared.result.Result

interface ISummaryBalanceDataSource {
    suspend fun getBalanceSummary(): Result<BalanceSummaryModel>
}